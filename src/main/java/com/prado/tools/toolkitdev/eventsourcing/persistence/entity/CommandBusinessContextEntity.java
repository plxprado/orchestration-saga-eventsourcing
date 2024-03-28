package com.prado.tools.toolkitdev.eventsourcing.persistence.entity;

import com.prado.tools.toolkitdev.eventsourcing.domain.vo.CommandBusinessContext;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Cacheable
@Entity
@Table(name = "command_business_context")
public class CommandBusinessContextEntity {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY,
            generator = "sq_command_business_context_id"
    )
    @SequenceGenerator(name = "sq_command_business_context_id", allocationSize = 1)
    private Long id;


    @Column(name = "name", nullable = false)
    private String name;


    @Column(name = "creation_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime creationDate;


    public CommandBusinessContext toVO() {
        return new CommandBusinessContext(this.id, this.name);
    }
}
