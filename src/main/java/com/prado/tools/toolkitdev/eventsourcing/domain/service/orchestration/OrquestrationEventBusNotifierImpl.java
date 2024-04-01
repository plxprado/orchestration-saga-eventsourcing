package com.prado.tools.toolkitdev.eventsourcing.domain.service.orchestration;

import com.prado.tools.toolkitdev.eventsourcing.domain.service.TransctionOrchestrationManager;
import com.prado.tools.toolkitdev.eventsourcing.event.handler.EventTransactionFlowPublisherEvent;
import com.prado.tools.toolkitdev.eventsourcing.domain.vo.EventTransactionContext;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class OrquestrationEventBusNotifierImpl implements OrquestrationEventBusNotifier {
    private ApplicationEventPublisher applicationEventPublisher;

    public OrquestrationEventBusNotifierImpl(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public void notifyOrchestrationHandler(EventTransactionContext eventTransactionContext) {
        this.applicationEventPublisher.publishEvent(new EventTransactionFlowPublisherEvent(eventTransactionContext));
    }

    @Override
    public void notifyEventBusSagaFinished(EventTransactionContext eventTransactionContext) {
        System.out.println("Event Transaction Context: [FINISHED]" + eventTransactionContext.toString());
    }
}
