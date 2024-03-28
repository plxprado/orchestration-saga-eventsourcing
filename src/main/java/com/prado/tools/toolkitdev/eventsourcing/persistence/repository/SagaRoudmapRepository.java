package com.prado.tools.toolkitdev.eventsourcing.persistence.repository;

import com.prado.tools.toolkitdev.eventsourcing.domain.vo.CommandBusinessContext;
import com.prado.tools.toolkitdev.eventsourcing.domain.vo.SagaRoudmap;
import com.prado.tools.toolkitdev.eventsourcing.domain.vo.SagaRoudmapItem;
import com.prado.tools.toolkitdev.eventsourcing.persistence.entity.SagaRoudmapEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface SagaRoudmapRepository extends CrudRepository<SagaRoudmapEntity, Long> {

    @Query("SELECT s FROM SagaRoudmapEntity s WHERE s.commandBusinessContext.name = :commandName")
    Optional<SagaRoudmapEntity> findByCommandBusinessName(String commandName);

    @Query("SELECT s FROM SagaRoudmapEntity s WHERE s.commandBusinessContext.id = :id")
    Optional<SagaRoudmapEntity> findByCommandBusinessContextId(Long id);

    @Query("SELECT s FROM SagaRoudmapEntity s")
    List<SagaRoudmapEntity> loadAll();

}
