package com.prado.tools.toolkitdev.eventsourcing.persistence.repository;

import com.prado.tools.toolkitdev.eventsourcing.persistence.entity.EventStreamEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.OptionalLong;
import java.util.UUID;

@Repository
public interface SagaEventStreamRepository extends CrudRepository<EventStreamEntity, Long> {
    List<EventStreamEntity> findByAggregationId(UUID aggregationId);


    @Query("SELECT MAX(e.version) FROM EventStreamEntity e WHERE e.aggregationId = :aggregationId")
    Optional<Long> findLastVersionByAggregationId(UUID aggregationId);
}
