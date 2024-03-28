package com.prado.tools.toolkitdev.eventsourcing.domain.service;

import com.prado.tools.toolkitdev.eventsourcing.domain.dto.CommandBusinessContextRequest;
import com.prado.tools.toolkitdev.eventsourcing.domain.dto.EventRequest;
import com.prado.tools.toolkitdev.eventsourcing.domain.factory.SagaCommandTransactionFactory;
import com.prado.tools.toolkitdev.eventsourcing.domain.ports.stream.EventStreamAppenderPort;
import com.prado.tools.toolkitdev.eventsourcing.domain.ports.OrchestraionManagerPort;
import com.prado.tools.toolkitdev.eventsourcing.domain.ports.persistence.SagaPersistencePort;
import com.prado.tools.toolkitdev.eventsourcing.domain.ports.projection.SagaProjectionDataPort;
import com.prado.tools.toolkitdev.eventsourcing.domain.service.aggregation.AggregationManager;
import com.prado.tools.toolkitdev.eventsourcing.domain.service.commands.CommandTransaction;
import com.prado.tools.toolkitdev.eventsourcing.domain.service.orchestration.OrquestrationEventBusNotifier;
import com.prado.tools.toolkitdev.eventsourcing.domain.vo.AggregationController;
import com.prado.tools.toolkitdev.eventsourcing.domain.vo.CommandType;
import com.prado.tools.toolkitdev.eventsourcing.domain.vo.Event;
import com.prado.tools.toolkitdev.eventsourcing.domain.vo.EventTransactionContext;
import com.prado.tools.toolkitdev.eventsourcing.domain.vo.ProcessCommandStatus;
import com.prado.tools.toolkitdev.eventsourcing.domain.vo.SagaRoudmap;
import com.prado.tools.toolkitdev.eventsourcing.domain.vo.SagaRoudmapItem;
import com.prado.tools.toolkitdev.eventsourcing.domain.vo.SagaRoudmapIterator;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.UUID;

import static com.prado.tools.toolkitdev.eventsourcing.domain.vo.ProcessCommandStatus.COMPLETED;

@Service
public class TransctionOrchestrationManager implements OrchestraionManagerPort {

    private SagaPersistencePort sagaPersistencePort;

    private SagaProjectionDataPort sagaProjectionDataPort;
    private AggregationManager  aggregationManager;
    private SagaCommandTransactionFactory sagaCommandTransactionFactory;
    private EventStreamAppenderPort eventStreamAppenderPort;

    private OrquestrationEventBusNotifier orquestrationEventBusNotifier;

    public TransctionOrchestrationManager(final SagaPersistencePort sagaPersistencePort,
                                          final SagaCommandTransactionFactory sagaCommandTransactionFactory,
                                          final AggregationManager aggregationManager,
                                          final OrquestrationEventBusNotifier orquestrationEventBusNotifier) {

        this.aggregationManager = aggregationManager;
        this.sagaPersistencePort = sagaPersistencePort;
        this.sagaCommandTransactionFactory = sagaCommandTransactionFactory;

    }

    public EventTransactionContext initSagaTransaction(final EventRequest eventRequest) {

        AggregationController aggregationController = this.aggregationManager.createAgregationToSaga(eventRequest.toVo());

        final SagaRoudmap sagaRoudmap = sagaPersistencePort
                .searchRoudmapByCommandBusinessContext(CommandBusinessContextRequest.builder()
                        .commandName(eventRequest.commandBusiness())
                        .build());

        final SagaRoudmapItem initCommandSaga = sagaRoudmap.getSagaRoudmapItemList().stream()
                .min(Comparator.comparing(SagaRoudmapItem::getStepOrder))
                .orElseThrow(() -> new RuntimeException("No step found"));

        final CommandTransaction commandTransaction = this.sagaCommandTransactionFactory
                .getCommandTransaction(CommandType.valueOf(initCommandSaga.getStepName().toUpperCase()));

        final EventTransactionContext eventTransactionContext = EventTransactionContext.builder()
                .aggregationId(aggregationController.getId())
                .processCommandStatus(ProcessCommandStatus.PENDING)
                .sagaRoudmapItem(initCommandSaga)
                .build();

        this.eventStreamAppenderPort.appendToEventStream(eventTransactionContext);

        return commandTransaction.executeCommand(eventTransactionContext);

    }

    @Override
    public EventTransactionContext executeNextBusinessCommand(final UUID aggregationId) {
        final EventTransactionContext eventTransactionContext = this.sagaProjectionDataPort.replayToCurrentState(aggregationId);

        if(isSagaFinished(eventTransactionContext)){
            orquestrationEventBusNotifier.notifyEventBusSagaFinished(eventTransactionContext);
            return eventTransactionContext;
        }

        final SagaRoudmapItem nextItem = getNextItem(eventTransactionContext);



        final EventTransactionContext newContext =  EventTransactionContext.builder()
                .aggregationId(eventTransactionContext.getAggregationId())
                .processCommandStatus(ProcessCommandStatus.PENDING)
                .sagaRoudmapItem(nextItem)
                .build();

        this.eventStreamAppenderPort.appendToEventStream(eventTransactionContext);

        CommandType command = CommandType.valueOf(nextItem.getStepName().toUpperCase());
        CommandTransaction commandTransaction = this.sagaCommandTransactionFactory
                .getCommandTransaction(command);

        return commandTransaction.executeCommand(newContext);
    }

    private boolean isSagaFinished(EventTransactionContext eventTransactionContext) {
        return (COMPLETED.equals(eventTransactionContext.getProcessCommandStatus())
            && eventTransactionContext.getSagaRoudmapItem().getFinalizer());

    }

    private SagaRoudmapItem getNextItem(EventTransactionContext eventTransactionContext) {
        final Long sagaRoudmapId = eventTransactionContext.getSagaRoudmapItem().getSagaRoudmap().getId();
        SagaRoudmapIterator itemIterator = sagaPersistencePort.searchRoudmapIteratorById(sagaRoudmapId);
        return itemIterator.findNextItem(eventTransactionContext.getSagaRoudmapItem());
    }




}
