package com.prado.tools.toolkitdev.eventsourcing.domain.service.exernal.calculation.handler;

import com.prado.tools.toolkitdev.eventsourcing.domain.ports.OrchestraionManagerPort;
import com.prado.tools.toolkitdev.eventsourcing.domain.ports.projection.SagaProjectionDataPort;
import com.prado.tools.toolkitdev.eventsourcing.domain.ports.stream.EventStreamAppenderPort;
import com.prado.tools.toolkitdev.eventsourcing.domain.service.exernal.calculation.EventCalculationDTO;
import com.prado.tools.toolkitdev.eventsourcing.domain.service.exernal.calculation.ResponseCalculation;
import com.prado.tools.toolkitdev.eventsourcing.domain.vo.EventTransactionContext;
import com.prado.tools.toolkitdev.eventsourcing.domain.vo.ProcessCommandStatus;
import com.prado.tools.toolkitdev.eventsourcing.domain.vo.TransactionJson;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class CalculationIntegrationHandler {

    private EventStreamAppenderPort eventStreamAppenderPort;

    private SagaProjectionDataPort sagaProjectionDataPort;

    private OrchestraionManagerPort orchestraionManagerPort;

    public CalculationIntegrationHandler(EventStreamAppenderPort eventStreamAppenderPort, SagaProjectionDataPort sagaProjectionDataPort, OrchestraionManagerPort orchestraionManagerPort) {
        this.eventStreamAppenderPort = eventStreamAppenderPort;
        this.sagaProjectionDataPort = sagaProjectionDataPort;
        this.orchestraionManagerPort = orchestraionManagerPort;
    }
    @Async
    @EventListener
    public void handle(EventCalculationSimulation calculationSimulation) {
        EventCalculationDTO fromMicroservices = calculationSimulation.getEventCalculationDTO();

        // TODO momento de carregar replay e como gerenciar os itens em exeucção
        EventTransactionContext currenteTransactionContext = this.sagaProjectionDataPort.replayToCurrentState(fromMicroservices.getAggregationId());
        ResponseCalculation responseCalculation = fromMicroservices.getResponseCalculation();
        final EventTransactionContext eventTransactionContext = EventTransactionContext.builder()
                .transactionJson(TransactionJson.builder()
                        .status(responseCalculation.getCalculationStatus())
                        .transactionExecutionDate(responseCalculation.getCalculationDate())
                        .transactionValueCalculated(responseCalculation.getCalculationResult())
                        .build())
                .sagaRoudmapItem(currenteTransactionContext.getSagaRoudmapItem())
                .processCommandStatus(ProcessCommandStatus.COMPLETED)
                .aggregationId(fromMicroservices.getAggregationId())
                .build();
        this.eventStreamAppenderPort.appendToEventStream(eventTransactionContext);
        this.orchestraionManagerPort.executeNextBusinessCommand(eventTransactionContext.getAggregationId());

    }
}
