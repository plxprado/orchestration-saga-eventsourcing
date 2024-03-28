package com.prado.tools.toolkitdev.eventsourcing.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;

@Entity
@Table(name = "process_saga_status")
@Getter
@Builder
public class ProcessSagaStatusEntity {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY,
            generator = "sq_process_saga_status_id"
    )
    @SequenceGenerator(name = "sq_process_saga_status_id", allocationSize = 1)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

}
