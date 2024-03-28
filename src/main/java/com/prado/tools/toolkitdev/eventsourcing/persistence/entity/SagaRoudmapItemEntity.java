package com.prado.tools.toolkitdev.eventsourcing.persistence.entity;

import com.prado.tools.toolkitdev.eventsourcing.domain.vo.SagaRoudmapItem;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.cache.annotation.Cacheable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Cacheable
@Entity
@Table(name = "saga_roudmap_item")
public class SagaRoudmapItemEntity {


    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY,
            generator = "sq_saga_roudmap_item_id"
    )
    @SequenceGenerator(name = "sq_saga_roudmap_item_id", allocationSize = 1)
    private Long id;

    @Column(name = "step_order", nullable = false)
    private Long stepOrder;

    @Column(name = "step_name", nullable = false)
    private String stepName;

    @Column(name = "finalizer")
    private Boolean finalizer;

    @ManyToOne
    @JoinColumn(name="saga_roudmap_id")
    private SagaRoudmapEntity sagaRoudmap;

    public SagaRoudmapItem toVO() {
        return SagaRoudmapItem.builder()
                .id(this.id)
                .sagaRoudmap(this.sagaRoudmap.toVO())
                .stepOrder(this.stepOrder)
                .stepName(this.stepName)
                .finalizer(this.finalizer)
                .build();
    }
}
