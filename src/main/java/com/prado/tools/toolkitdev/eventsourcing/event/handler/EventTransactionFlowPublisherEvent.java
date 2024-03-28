package com.prado.tools.toolkitdev.eventsourcing.event.handler;

import com.prado.tools.toolkitdev.eventsourcing.domain.vo.EventTransactionContext;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class EventTransactionFlowPublisherEvent extends ApplicationEvent {

    private EventTransactionContext eventTransactionContext;
    public EventTransactionFlowPublisherEvent(EventTransactionContext eventTransactionContext) {
        super(eventTransactionContext);
        this.eventTransactionContext = eventTransactionContext;
    }
}
