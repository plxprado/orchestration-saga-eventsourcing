package com.prado.tools.toolkitdev.eventsourcing.domain.service.commands;

import com.prado.tools.toolkitdev.eventsourcing.domain.ports.stream.EventStreamAppenderPort;
import com.prado.tools.toolkitdev.eventsourcing.domain.service.orchestration.OrquestrationEventBusNotifier;
import com.prado.tools.toolkitdev.eventsourcing.domain.vo.EventTransactionContext;
import com.prado.tools.toolkitdev.eventsourcing.domain.vo.ProcessCommandStatusEnum;
import com.prado.tools.toolkitdev.eventsourcing.domain.vo.objectstream.ChargeStreamObject;
import com.prado.tools.toolkitdev.eventsourcing.domain.vo.objectstream.EventContentStreamObject;
import com.prado.tools.toolkitdev.eventsourcing.domain.vo.objectstream.EventStreamObject;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ChargeCommandTransaction implements CommandTransaction {


    private EventStreamAppenderPort eventStreamAppenderPort;
    private OrquestrationEventBusNotifier orquestrationEventBusNotifier;


    public ChargeCommandTransaction(EventStreamAppenderPort eventStreamAppenderPort,
                                    OrquestrationEventBusNotifier orquestrationEventBusNotifier) {

        this.eventStreamAppenderPort = eventStreamAppenderPort;
        this.orquestrationEventBusNotifier = orquestrationEventBusNotifier;
    }
    @Override
    public EventTransactionContext executeCommand(EventTransactionContext eventTransactionContext) {
        final EventStreamObject eventStreamObject = EventStreamObject.builder()
                .sagaName(eventTransactionContext.getSagaWorkflowItem().getSagaWorkflow().getName())
                .aggregationId(eventTransactionContext.getAggregationId())
                .status("COMPLETED")
                .transactionExecutionDate(LocalDateTime.now())
                .chargeStreamObject(ChargeStreamObject.builder()
                        .chargeType("CHARGE")
                        .chargeStauts("COMPLETED")
                        .build())
                .build();
        final EventTransactionContext updated = eventTransactionContext.updateSagaProcessStatusAndPayload(ProcessCommandStatusEnum.COMPLETED, eventStreamObject);

        this.eventStreamAppenderPort.appendToEventStream(updated);

        orquestrationEventBusNotifier.notifyOrchestrationHandler(updated);

        return updated;
    }


}
