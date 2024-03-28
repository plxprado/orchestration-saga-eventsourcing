package com.prado.tools.toolkitdev.eventsourcing.persistence.repository;

import com.prado.tools.toolkitdev.eventsourcing.persistence.entity.CommandBusinessContextEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommandBusinessContextRepository extends CrudRepository<CommandBusinessContextEntity, Long>{
    Optional<CommandBusinessContextEntity> findByName(String name);
}
