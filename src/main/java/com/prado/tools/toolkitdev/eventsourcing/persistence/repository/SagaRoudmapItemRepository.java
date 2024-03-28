package com.prado.tools.toolkitdev.eventsourcing.persistence.repository;

import com.prado.tools.toolkitdev.eventsourcing.persistence.entity.SagaRoudmapItemEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SagaRoudmapItemRepository extends CrudRepository<SagaRoudmapItemEntity, Long>{
}
