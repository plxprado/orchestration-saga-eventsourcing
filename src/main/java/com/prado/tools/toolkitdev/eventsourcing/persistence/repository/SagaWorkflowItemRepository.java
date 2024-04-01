package com.prado.tools.toolkitdev.eventsourcing.persistence.repository;

import com.prado.tools.toolkitdev.eventsourcing.persistence.entity.SagaWorkflowItemEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SagaWorkflowItemRepository extends CrudRepository<SagaWorkflowItemEntity, Long>{
}
