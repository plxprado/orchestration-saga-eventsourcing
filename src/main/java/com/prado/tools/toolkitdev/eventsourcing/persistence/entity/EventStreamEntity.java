package com.prado.tools.toolkitdev.eventsourcing.persistence.entity;

import com.prado.tools.toolkitdev.eventsourcing.domain.vo.EventTransactionContext;
import com.prado.tools.toolkitdev.eventsourcing.domain.vo.ProcessCommandStatusEnum;
import com.prado.tools.toolkitdev.eventsourcing.domain.vo.objectstream.EventStreamObject;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "event_stream")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class EventStreamEntity {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY,
            generator = "sq_saga_event_stream_id"
    )
    @SequenceGenerator(name = "sq_saga_event_stream_id", allocationSize = 1)
    private Long id;

    @Column(name = "status", nullable = false)
    private String status;


    @ManyToOne
    @JoinColumn(name = "workflow_item_id")
    private SagaWorkflowItemEntity workflowItem;


    @Column(name = "aggregation_id", nullable = false)
    private UUID aggregationId;


    @Column(name = "date_processed", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime dateProcessed;


    @Column(name = "transaction_json")
    @JdbcTypeCode(SqlTypes.JSON)
    private EventStreamObject eventStreamObject;

    @ManyToOne
    @JoinColumn(name = "process_status_id")
    private ProcessStatusEntity progressSagaStatus;

    @Column(name = "version", nullable = false)
    private Long version;



    public EventTransactionContext toTransactionContext() {
        return EventTransactionContext.builder()
                .aggregationId(this.aggregationId)
                .processCommandStatusEnum(ProcessCommandStatusEnum.valueOf(this.progressSagaStatus.getName()))
                .sagaWorkflowItem(this.workflowItem.toVO())
                .eventStreamObject(this.eventStreamObject)
                .build();
    }
}
