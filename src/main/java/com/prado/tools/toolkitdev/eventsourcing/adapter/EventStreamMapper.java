package com.prado.tools.toolkitdev.eventsourcing.adapter;

import com.prado.tools.toolkitdev.eventsourcing.domain.vo.EventTransactionContext;
import com.prado.tools.toolkitdev.eventsourcing.domain.vo.ProcessCommandStatusEnum;
import com.prado.tools.toolkitdev.eventsourcing.persistence.entity.EventStreamEntity;
import org.springframework.stereotype.Component;

@Component
public class EventStreamMapper {

    public EventTransactionContext fromEntityToTransactionContext(final EventStreamEntity eventStreamEntity,
                                                                  final EventTransactionContext transactionContext) {
        return EventTransactionContext.builder()
                .aggregationId(eventStreamEntity.getAggregationId())
                .sagaWorkflowItem(transactionContext.getSagaWorkflowItem())

                .processCommandStatusEnum(ProcessCommandStatusEnum.valueOf(eventStreamEntity.getStatus()))
                .build();
    }
}
