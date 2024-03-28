package com.prado.tools.toolkitdev.eventsourcing.persistence.repository;

import com.prado.tools.toolkitdev.eventsourcing.domain.vo.EventTransactionContext;
import com.prado.tools.toolkitdev.eventsourcing.persistence.entity.SagaEventStreamEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SagaEventStreamRepository extends CrudRepository<SagaEventStreamEntity, Long> {
    Optional<SagaEventStreamEntity> findByAggregationId(UUID aggregationId);


}
