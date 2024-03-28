package com.prado.tools.toolkitdev.eventsourcing.domain.service.aggregation;

import com.prado.tools.toolkitdev.eventsourcing.domain.vo.AggregationController;
import com.prado.tools.toolkitdev.eventsourcing.domain.vo.Event;
import com.prado.tools.toolkitdev.eventsourcing.domain.vo.EventTransactionContext;

public interface AggregationManager {

    AggregationController createAgregationToSaga(Event eventTransactionContext);
}
