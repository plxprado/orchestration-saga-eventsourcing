package com.prado.tools.toolkitdev.eventsourcing.domain.vo;

import com.prado.tools.toolkitdev.eventsourcing.domain.vo.objectstream.EventStreamObject;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Builder
@Getter
public class EventTransactionContext {

    private UUID aggregationId;
    private UUID externalTransactionId;
    private SagaWorkflowItem sagaWorkflowItem;
    private ProcessCommandStatusEnum processCommandStatusEnum;
    private EventStreamObject eventStreamObject;

    public EventTransactionContext updateSagaProcessStatus(final ProcessCommandStatusEnum processCommandStatusEnumNew,
                                                           final SagaWorkflowItem sagaWorkflowItemNew) {
        return EventTransactionContext.builder()
                .aggregationId(this.aggregationId)
                .externalTransactionId(this.externalTransactionId)
                .sagaWorkflowItem(sagaWorkflowItemNew)
                .processCommandStatusEnum(processCommandStatusEnumNew)
                .eventStreamObject(this.eventStreamObject)
                .build();
    }

    public EventTransactionContext updateSagaProcessStatus(final ProcessCommandStatusEnum processCommandStatusEnumNew) {
        return EventTransactionContext.builder()
                .aggregationId(this.aggregationId)
                .externalTransactionId(this.externalTransactionId)
                .sagaWorkflowItem(this.sagaWorkflowItem)
                .processCommandStatusEnum(processCommandStatusEnumNew)
                .eventStreamObject(this.eventStreamObject)
                .build();
    }

    public EventTransactionContext updateSagaProcessStatusAndPayload(final ProcessCommandStatusEnum processCommandStatusEnumNew, final EventStreamObject eventStreamObjectNew) {
        return EventTransactionContext.builder()
                .aggregationId(this.aggregationId)
                .externalTransactionId(this.externalTransactionId)
                .sagaWorkflowItem(this.sagaWorkflowItem)
                .processCommandStatusEnum(processCommandStatusEnumNew)
                .eventStreamObject(eventStreamObjectNew)
                .build();
    }

}
