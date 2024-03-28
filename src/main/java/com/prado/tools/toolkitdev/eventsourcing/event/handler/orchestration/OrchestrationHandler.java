package com.prado.tools.toolkitdev.eventsourcing.event.handler.orchestration;

import com.prado.tools.toolkitdev.eventsourcing.domain.ports.OrchestraionManagerPort;
import com.prado.tools.toolkitdev.eventsourcing.domain.vo.EventTransactionContext;
import com.prado.tools.toolkitdev.eventsourcing.event.handler.EventTransactionFlowPublisherEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class OrchestrationHandler {

    private OrchestraionManagerPort orchestraionManagerPort;

    public OrchestrationHandler(OrchestraionManagerPort orchestraionManagerPort) {
        this.orchestraionManagerPort = orchestraionManagerPort;
    }
    @Async
    @EventListener
    public void handle(EventTransactionFlowPublisherEvent eventTransactionFlowPublisherEvent) {
        EventTransactionContext eventTransactionContext = eventTransactionFlowPublisherEvent.getEventTransactionContext();
        this.orchestraionManagerPort.executeNextBusinessCommand(eventTransactionContext.getAggregationId());
    }
}
