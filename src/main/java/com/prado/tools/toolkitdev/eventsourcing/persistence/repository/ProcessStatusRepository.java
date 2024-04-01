package com.prado.tools.toolkitdev.eventsourcing.persistence.repository;

import com.prado.tools.toolkitdev.eventsourcing.persistence.entity.ProcessStatusEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProcessStatusRepository extends CrudRepository<ProcessStatusEntity, Long> {
    Optional<ProcessStatusEntity> findByName(String name);
}
