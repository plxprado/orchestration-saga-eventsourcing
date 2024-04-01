package com.prado.tools.toolkitdev.eventsourcing.persistence.entity;

import com.prado.tools.toolkitdev.eventsourcing.domain.vo.ProcessCommandStatus;
import com.prado.tools.toolkitdev.eventsourcing.domain.vo.ProcessCommandStatusEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.cache.annotation.Cacheable;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Builder
@Cacheable
@Table(name = "process_saga_status")
public class ProcessStatusEntity {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY,
            generator = "sq_process_saga_status_id"
    )
    @SequenceGenerator(name = "sq_process_saga_status_id", allocationSize = 1)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    public ProcessCommandStatus toVo() {
        return ProcessCommandStatus.builder()
                .id(this.id)
                .status(ProcessCommandStatusEnum.valueOf(this.name))
                .build();
    }
}
