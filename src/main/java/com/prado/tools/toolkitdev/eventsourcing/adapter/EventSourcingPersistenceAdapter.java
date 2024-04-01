package com.prado.tools.toolkitdev.eventsourcing.adapter;

import com.prado.tools.toolkitdev.eventsourcing.domain.ports.persistence.EventSourcingPersitencePort;
import com.prado.tools.toolkitdev.eventsourcing.domain.vo.AggregationController;
import com.prado.tools.toolkitdev.eventsourcing.domain.vo.StepCommandBusiness;
import com.prado.tools.toolkitdev.eventsourcing.domain.vo.Event;
import com.prado.tools.toolkitdev.eventsourcing.persistence.entity.AggregationControllerEntity;
import com.prado.tools.toolkitdev.eventsourcing.persistence.entity.SagaWorkflowEntity;
import com.prado.tools.toolkitdev.eventsourcing.persistence.repository.AggregationControllerRepository;
import com.prado.tools.toolkitdev.eventsourcing.persistence.repository.SagaWorkflowRepository;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class EventSourcingPersistenceAdapter implements EventSourcingPersitencePort {


    private static final Long FIRST_VERSION = 1L;
    private  AggregationControllerRepository aggregationControllerRepository;

    private SagaWorkflowRepository sagaWorkflowRepository;

    public EventSourcingPersistenceAdapter(AggregationControllerRepository aggregationControllerRepository, SagaWorkflowRepository sagaWorkflowRepository) {
        this.aggregationControllerRepository = aggregationControllerRepository;
        this.sagaWorkflowRepository = sagaWorkflowRepository;
    }
    @Override
    public AggregationController createEventAgregration(Event event) throws DuplicateKeyException {


        final SagaWorkflowEntity sagaWorkflowEntity = this.sagaWorkflowRepository
                .findByName(event.getSagaName())
                .orElseThrow(() -> new RuntimeException("No saga found"));


        final AggregationControllerEntity aggregationEntity = AggregationControllerEntity.builder()
                .sagaWorkflow(sagaWorkflowEntity)
                .transactionId(event.getExternalTransactionId())
                .creationDate(LocalDateTime.now())
                .version(FIRST_VERSION)
                .build();

        AggregationControllerEntity aggregationController = aggregationControllerRepository.save(aggregationEntity);

        return aggregationController.toVO();
    }
}
