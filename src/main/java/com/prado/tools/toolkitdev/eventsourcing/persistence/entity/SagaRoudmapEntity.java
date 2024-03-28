package com.prado.tools.toolkitdev.eventsourcing.persistence.entity;

import com.prado.tools.toolkitdev.eventsourcing.domain.vo.SagaRoudmap;
import com.prado.tools.toolkitdev.eventsourcing.domain.vo.SagaRoudmapItem;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.cache.annotation.Cacheable;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Cacheable
@Entity
@Table(name = "saga_roudmap")
public class SagaRoudmapEntity {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY,
            generator = "sq_saga_saga_roudmap_id"
    )
    @SequenceGenerator(name = "sq_saga_saga_roudmap_id", allocationSize = 1)
    private Long id;

    @Column(name = "creation_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime creationDate;

    @ManyToOne
    @JoinColumn(name = "command_id")
    private CommandBusinessContextEntity commandBusinessContext;

    @OneToMany(mappedBy="sagaRoudmap", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<SagaRoudmapItemEntity> items;

    public SagaRoudmap toVO() {
        return SagaRoudmap.builder()
                .id(this.id)
                .creationDate(this.creationDate)
                .commandBusinessContext(this.commandBusinessContext.toVO())
                .sagaRoudmapItemList(Collections.emptyList())
                .build();
    }


    public SagaRoudmap toVOWithList() {
        return SagaRoudmap.builder()
                .id(this.id)
                .creationDate(this.creationDate)
                .commandBusinessContext(this.commandBusinessContext.toVO())
                .sagaRoudmapItemList(Optional.ofNullable(this.items)
                        .map(items -> items.stream()
                                .map(SagaRoudmapItemEntity::toVO)
                                .toList())
                        .orElse(Collections.emptyList()))
                .build();
    }
}
