package com.prado.tools.toolkitdev.eventsourcing.persistence.repository;

import com.prado.tools.toolkitdev.eventsourcing.persistence.entity.EventContentEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface EventRepository extends CrudRepository<EventContentEntity, UUID>{

    Optional<EventContentEntity> findByAggregationId(UUID aggregationId);
}
