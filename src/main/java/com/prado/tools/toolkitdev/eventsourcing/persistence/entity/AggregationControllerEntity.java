package com.prado.tools.toolkitdev.eventsourcing.persistence.entity;

import com.prado.tools.toolkitdev.eventsourcing.domain.vo.AggregationController;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;




@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Entity
@Table(name = "aggregation_controller")
public class AggregationControllerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "related_id")
    private UUID relatedId;

    @Column(name = "id_transaction_event", nullable = false)
    private UUID transactionId;

    @Column(name = "creation_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime creationDate;

    @ManyToOne
    @JoinColumn(name = "saga_workflow_id")
    private SagaWorkflowEntity sagaWorkflow;

    @Column(name = "version", nullable = false)
    private Long version;


    public AggregationController toVO() {
        return AggregationController.builder()
                .id(id)
                .transactionId(transactionId)
                .creationDate(creationDate)
                .sagaWorkflow(sagaWorkflow.toVO())
                .version(version)
                .build();

    }
}
