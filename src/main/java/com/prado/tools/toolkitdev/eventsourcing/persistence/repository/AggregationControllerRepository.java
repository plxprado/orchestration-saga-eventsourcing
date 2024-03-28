package com.prado.tools.toolkitdev.eventsourcing.persistence.repository;

import com.prado.tools.toolkitdev.eventsourcing.persistence.entity.AggregationControllerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AggregationControllerRepository extends JpaRepository<AggregationControllerEntity, UUID>{
}
