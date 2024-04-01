package com.prado.tools.toolkitdev.eventsourcing.domain.service.eventstream;

import com.prado.tools.toolkitdev.eventsourcing.adapter.EventStreamMapper;
import com.prado.tools.toolkitdev.eventsourcing.domain.ports.stream.EventStreamAppenderPort;
import com.prado.tools.toolkitdev.eventsourcing.domain.vo.EventTransactionContext;
import com.prado.tools.toolkitdev.eventsourcing.persistence.entity.EventStreamEntity;
import com.prado.tools.toolkitdev.eventsourcing.persistence.entity.ProcessStatusEntity;
import com.prado.tools.toolkitdev.eventsourcing.persistence.entity.SagaWorkflowItemEntity;
import com.prado.tools.toolkitdev.eventsourcing.persistence.repository.ProcessStatusRepository;
import com.prado.tools.toolkitdev.eventsourcing.persistence.repository.SagaEventStreamRepository;
import com.prado.tools.toolkitdev.eventsourcing.persistence.repository.SagaWorkflowItemRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class EventStreamAppenderImpl implements EventStreamAppenderPort {

    private SagaEventStreamRepository sagaEventStreamRepository;

    private SagaWorkflowItemRepository sagaWorkflowItemRepository;

    private ProcessStatusRepository processStatusRepository;

    private EventStreamMapper eventStreamMapper;

    private static final long PLUS_VERSION = 1;

    private static final long INITIAl_VERSION = 0;

    public EventStreamAppenderImpl(SagaEventStreamRepository sagaEventStreamRepository,
                                   SagaWorkflowItemRepository sagaWorkflowItemRepository,
                                   ProcessStatusRepository processStatusRepository,
                                   EventStreamMapper eventStreamMapper) {
        this.sagaEventStreamRepository = sagaEventStreamRepository;
        this.sagaWorkflowItemRepository = sagaWorkflowItemRepository;
        this.processStatusRepository = processStatusRepository;
        this.eventStreamMapper = eventStreamMapper;
    }
    @Override
    public EventTransactionContext appendToEventStream(EventTransactionContext eventTransactionContext) {

        final SagaWorkflowItemEntity sagaWorkflowItemEntity = sagaWorkflowItemRepository.findById(eventTransactionContext.getSagaWorkflowItem().getId())
                .orElseThrow(() -> new RuntimeException("No saga roudmap item found"));

        final ProcessStatusEntity processStatusEntity = processStatusRepository.findByName(eventTransactionContext.getProcessCommandStatusEnum().name())
                .orElseThrow(() -> new RuntimeException("No process status found"));

       final Long currentVersion = this.sagaEventStreamRepository.findLastVersionByAggregationId(eventTransactionContext.getAggregationId())
               .orElse(INITIAl_VERSION);


        final EventStreamEntity eventStreamEntity = EventStreamEntity.builder()
                .workflowItem(sagaWorkflowItemEntity)
                .progressSagaStatus(processStatusEntity)
                .aggregationId(eventTransactionContext.getAggregationId())
                .eventStreamObject(eventTransactionContext.getEventStreamObject())
                .dateProcessed(LocalDateTime.now())
                .version((currentVersion + PLUS_VERSION))
                .status(eventTransactionContext.getProcessCommandStatusEnum().name())
                .build();

        final EventStreamEntity fromDatabase = this.sagaEventStreamRepository.save(eventStreamEntity);
        return eventStreamMapper.fromEntityToTransactionContext(fromDatabase, eventTransactionContext);
    }




}
