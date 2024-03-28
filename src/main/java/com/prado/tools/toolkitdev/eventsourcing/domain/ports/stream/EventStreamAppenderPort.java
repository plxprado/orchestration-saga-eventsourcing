package com.prado.tools.toolkitdev.eventsourcing.domain.ports.stream;

import com.prado.tools.toolkitdev.eventsourcing.domain.vo.EventTransactionContext;

public interface EventStreamAppenderPort {

    EventTransactionContext appendToEventStream(EventTransactionContext eventTransactionContext);
}
