package com.prado.tools.toolkitdev.eventsourcing.adapter;

import com.prado.tools.toolkitdev.eventsourcing.domain.vo.EventTransactionContext;
import com.prado.tools.toolkitdev.eventsourcing.domain.vo.ProcessCommandStatus;
import com.prado.tools.toolkitdev.eventsourcing.persistence.entity.SagaEventStreamEntity;
import org.springframework.stereotype.Component;

@Component
public class EventStreamMapper {

    public EventTransactionContext fromEntityToTransactionContext(final SagaEventStreamEntity sagaEventStreamEntity,
                                                                  final EventTransactionContext transactionContext) {
        return EventTransactionContext.builder()
                .aggregationId(sagaEventStreamEntity.getAggregationId())
                .sagaRoudmapItem(transactionContext.getSagaRoudmapItem())
                .processCommandStatus(ProcessCommandStatus.valueOf(sagaEventStreamEntity.getStatus()))
                .build();
    }
}
