package com.prado.tools.toolkitdev.eventsourcing.domain.service;

import com.prado.tools.toolkitdev.eventsourcing.domain.dto.EventRequest;
import com.prado.tools.toolkitdev.eventsourcing.domain.factory.SagaCommandTransactionFactory;
import com.prado.tools.toolkitdev.eventsourcing.domain.ports.OrchestraionManagerPort;
import com.prado.tools.toolkitdev.eventsourcing.domain.ports.persistence.SagaPersistencePort;
import com.prado.tools.toolkitdev.eventsourcing.domain.ports.projection.SagaProjectionDataPort;
import com.prado.tools.toolkitdev.eventsourcing.domain.ports.stream.EventStreamAppenderPort;
import com.prado.tools.toolkitdev.eventsourcing.domain.service.aggregation.AggregationManager;
import com.prado.tools.toolkitdev.eventsourcing.domain.service.commands.CommandTransaction;
import com.prado.tools.toolkitdev.eventsourcing.domain.service.orchestration.OrquestrationEventBusNotifier;
import com.prado.tools.toolkitdev.eventsourcing.domain.vo.AggregationController;
import com.prado.tools.toolkitdev.eventsourcing.domain.vo.EventTransactionContext;
import com.prado.tools.toolkitdev.eventsourcing.domain.vo.ProcessCommandStatusEnum;
import com.prado.tools.toolkitdev.eventsourcing.domain.vo.SagaWorkflow;
import com.prado.tools.toolkitdev.eventsourcing.domain.vo.SagaWorkflowItem;
import com.prado.tools.toolkitdev.eventsourcing.domain.vo.SagaWorkflowIterator;
import com.prado.tools.toolkitdev.eventsourcing.domain.vo.objectstream.EventContentStreamObject;
import com.prado.tools.toolkitdev.eventsourcing.domain.vo.objectstream.EventStreamObject;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.UUID;

import static com.prado.tools.toolkitdev.eventsourcing.domain.vo.ProcessCommandStatusEnum.COMPLETED;

@Service
public class TransctionOrchestrationManager implements OrchestraionManagerPort {

    private SagaPersistencePort sagaPersistencePort;
    private SagaProjectionDataPort sagaProjectionDataPort;
    private AggregationManager  aggregationManager;
    private SagaCommandTransactionFactory sagaCommandTransactionFactory;
    private EventStreamAppenderPort eventStreamAppenderPort;

    private OrquestrationEventBusNotifier orquestrationEventBusNotifier;

    public TransctionOrchestrationManager(SagaPersistencePort sagaPersistencePort,
                                          SagaProjectionDataPort sagaProjectionDataPort,
                                          AggregationManager aggregationManager,
                                          SagaCommandTransactionFactory sagaCommandTransactionFactory,
                                          EventStreamAppenderPort eventStreamAppenderPort,
                                          OrquestrationEventBusNotifier orquestrationEventBusNotifier) {
        this.sagaPersistencePort = sagaPersistencePort;
        this.sagaProjectionDataPort = sagaProjectionDataPort;
        this.aggregationManager = aggregationManager;
        this.sagaCommandTransactionFactory = sagaCommandTransactionFactory;
        this.eventStreamAppenderPort = eventStreamAppenderPort;
        this.orquestrationEventBusNotifier = orquestrationEventBusNotifier;
    }

    public EventTransactionContext initSagaTransaction(final EventRequest eventRequest) {

        AggregationController aggregationController = this.aggregationManager.createAgregationToSaga(eventRequest.toVo());


        final SagaWorkflow sagaWorkflow = sagaPersistencePort
                .searchSagaWorkflowByName(eventRequest.sagaWorfklowName());

        final SagaWorkflowItem initCommandSaga = sagaWorkflow.getSagaWorkflowItemList().stream()
                .min(Comparator.comparing(SagaWorkflowItem::getStepOrder))
                .orElseThrow(() -> new RuntimeException("No step found"));

        final CommandTransaction commandTransaction = this.sagaCommandTransactionFactory
                .getCommandTransaction(initCommandSaga.getStepCommandBusiness());



        final EventStreamObject payload = EventStreamObject.builder()
                .aggregationId(aggregationController.getId())
                .transactionExecutionDate(LocalDateTime.now())
                .sagaName(eventRequest.sagaWorfklowName())
                .status(ProcessCommandStatusEnum.PENDING.name())
                .eventContentStreamObject(EventContentStreamObject.builder()
                        .transactionValue(eventRequest.transactionValue())
                        .transactionDate(eventRequest.transactionDate())
                        .externalTransactionId(eventRequest.transactionId())
                        .transactionType(eventRequest.sagaWorfklowName())
                        .build())

                .build();

        final EventTransactionContext eventTransactionContext = EventTransactionContext.builder()
                .aggregationId(aggregationController.getId())
                .externalTransactionId(eventRequest.transactionId())
                .processCommandStatusEnum(ProcessCommandStatusEnum.PENDING)
                .sagaWorkflowItem(initCommandSaga)
                .eventStreamObject(payload)
                .build();

        this.eventStreamAppenderPort.appendToEventStream(eventTransactionContext);

        return commandTransaction.executeCommand(eventTransactionContext);

    }

    @Override
    public EventTransactionContext executeNextBusinessCommand(final UUID aggregationId) {
        final EventTransactionContext eventTransactionContext = this.sagaProjectionDataPort.replayToCurrentState(aggregationId);

        if(isSagaFinished(eventTransactionContext)){
            eventStreamAppenderPort.appendToEventStream(eventTransactionContext.updateSagaProcessStatus(ProcessCommandStatusEnum.SAGA_COMPLETED_FINISHED));
            orquestrationEventBusNotifier.notifyEventBusSagaFinished(eventTransactionContext);
            return eventTransactionContext;
        }

        final SagaWorkflowItem nextItem = getNextItem(eventTransactionContext);


        final EventTransactionContext newContext = eventTransactionContext.updateSagaProcessStatus(
                ProcessCommandStatusEnum.PENDING, nextItem);

        this.eventStreamAppenderPort.appendToEventStream(newContext);

        final CommandTransaction commandTransaction = this.sagaCommandTransactionFactory
                .getCommandTransaction(nextItem.getStepCommandBusiness());

        return commandTransaction.executeCommand(newContext);
    }

    private boolean isSagaFinished(EventTransactionContext eventTransactionContext) {
        return (COMPLETED.equals(eventTransactionContext.getProcessCommandStatusEnum())
            && eventTransactionContext.getSagaWorkflowItem().getFinalizer());

    }

    private SagaWorkflowItem getNextItem(EventTransactionContext eventTransactionContext) {
        final Long sagaRoudmapId = eventTransactionContext.getSagaWorkflowItem().getSagaWorkflow().getId();
        SagaWorkflowIterator itemIterator = sagaPersistencePort.searchRoudmapIteratorById(sagaRoudmapId);
        return itemIterator.findNextItem(eventTransactionContext.getSagaWorkflowItem());
    }




}
