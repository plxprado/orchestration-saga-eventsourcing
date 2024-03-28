package com.prado.tools.toolkitdev.eventsourcing.domain.service.eventstream;

import com.prado.tools.toolkitdev.eventsourcing.adapter.EventStreamMapper;
import com.prado.tools.toolkitdev.eventsourcing.domain.ports.stream.EventStreamAppenderPort;
import com.prado.tools.toolkitdev.eventsourcing.domain.vo.EventTransactionContext;
import com.prado.tools.toolkitdev.eventsourcing.persistence.entity.SagaEventStreamEntity;
import com.prado.tools.toolkitdev.eventsourcing.persistence.entity.SagaRoudmapItemEntity;
import com.prado.tools.toolkitdev.eventsourcing.persistence.repository.SagaEventStreamRepository;
import com.prado.tools.toolkitdev.eventsourcing.persistence.repository.SagaRoudmapItemRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class EventStreamAppenderImpl implements EventStreamAppenderPort {

    private SagaEventStreamRepository sagaEventStreamRepository;

    private SagaRoudmapItemRepository sagaRoudmapItemRepository;

    private EventStreamMapper eventStreamMapper;

    public EventStreamAppenderImpl(SagaEventStreamRepository sagaEventStreamRepository, SagaRoudmapItemRepository sagaRoudmapItemRepository, EventStreamMapper eventStreamMapper) {
        this.sagaEventStreamRepository = sagaEventStreamRepository;
        this.sagaRoudmapItemRepository = sagaRoudmapItemRepository;
        this.eventStreamMapper = eventStreamMapper;
    }

    @Override
    public EventTransactionContext appendToEventStream(EventTransactionContext eventTransactionContext) {

        SagaRoudmapItemEntity sagaRoudmapItemEntity = sagaRoudmapItemRepository.findById(eventTransactionContext.getSagaRoudmapItem().getId())
                .orElseThrow(() -> new RuntimeException("No saga roudmap item found"));
        final SagaEventStreamEntity sagaEventStreamEntity = SagaEventStreamEntity.builder()
                .roudmapItem(sagaRoudmapItemEntity)
                .aggregationId(eventTransactionContext.getAggregationId())
                .dateProcessed(LocalDateTime.now())
                .status(eventTransactionContext.getProcessCommandStatus().name())
                .build();

        final SagaEventStreamEntity fromDatabase = this.sagaEventStreamRepository.save(sagaEventStreamEntity);
        return eventStreamMapper.fromEntityToTransactionContext(fromDatabase, eventTransactionContext);
    }
}
