package com.prado.tools.toolkitdev.eventsourcing.domain.ports;

import com.prado.tools.toolkitdev.eventsourcing.domain.dto.EventRequest;
import com.prado.tools.toolkitdev.eventsourcing.domain.vo.EventTransactionContext;

import java.util.UUID;

public interface OrchestraionManagerPort {
    EventTransactionContext initSagaTransaction(final EventRequest eventRequest);

    EventTransactionContext executeNextBusinessCommand(final UUID aggregationId);

}
