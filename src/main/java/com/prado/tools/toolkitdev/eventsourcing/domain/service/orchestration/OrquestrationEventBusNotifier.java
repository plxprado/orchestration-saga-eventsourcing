package com.prado.tools.toolkitdev.eventsourcing.domain.service.orchestration;

import com.prado.tools.toolkitdev.eventsourcing.domain.vo.EventTransactionContext;

public interface OrquestrationEventBusNotifier {

    void notifyOrchestrationHandler(EventTransactionContext eventTransactionContext);

    void notifyEventBusSagaFinished(EventTransactionContext eventTransactionContext);
}
