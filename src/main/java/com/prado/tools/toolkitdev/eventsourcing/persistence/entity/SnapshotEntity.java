package com.prado.tools.toolkitdev.eventsourcing.persistence.entity;

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

import java.time.LocalDateTime;

@Entity
@Table(name = "snapshot")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class SnapshotEntity {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "sq_snapshot_id"
    )
    @SequenceGenerator(name = "sq_snapshot_id", allocationSize = 1)
    private Long id;

    @Column(name = "version", nullable = false)
    private Long version;

    @Column(name = "creation_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime creationDate;

    @ManyToOne
    @JoinColumn(name = "aggregation_id")
    private AggregationControllerEntity eventAggregation;

}

