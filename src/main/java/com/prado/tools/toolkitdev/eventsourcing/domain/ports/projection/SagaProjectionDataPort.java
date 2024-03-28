package com.prado.tools.toolkitdev.eventsourcing.domain.ports.projection;

import com.prado.tools.toolkitdev.eventsourcing.domain.vo.EventTransactionContext;

import java.util.UUID;

public interface SagaProjectionDataPort {

    EventTransactionContext replayToCurrentState(UUID aggregationId);
}
