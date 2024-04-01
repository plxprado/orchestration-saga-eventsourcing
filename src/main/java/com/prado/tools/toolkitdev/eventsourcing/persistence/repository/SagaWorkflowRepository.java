package com.prado.tools.toolkitdev.eventsourcing.persistence.repository;

import com.prado.tools.toolkitdev.eventsourcing.persistence.entity.SagaWorkflowEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SagaWorkflowRepository extends CrudRepository<SagaWorkflowEntity, Long> {


    @Query("SELECT s FROM SagaWorkflowEntity s")
    List<SagaWorkflowEntity> loadAll();

    Optional<SagaWorkflowEntity> findByName(String sagaName);
}
