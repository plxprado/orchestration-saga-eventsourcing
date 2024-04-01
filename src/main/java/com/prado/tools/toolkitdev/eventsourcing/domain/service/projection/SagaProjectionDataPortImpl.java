package com.prado.tools.toolkitdev.eventsourcing.domain.service.projection;

import com.prado.tools.toolkitdev.eventsourcing.domain.ports.projection.SagaProjectionDataPort;
import com.prado.tools.toolkitdev.eventsourcing.domain.vo.EventTransactionContext;
import com.prado.tools.toolkitdev.eventsourcing.persistence.entity.EventStreamEntity;
import com.prado.tools.toolkitdev.eventsourcing.persistence.repository.SagaEventStreamRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.UUID;


@Service
public class SagaProjectionDataPortImpl implements SagaProjectionDataPort {

    /**
     * TODO
     *  ler de projeções ja com snapshots, nao fazer replay de eventos por conta de peformance
     */
    private SagaEventStreamRepository eventStreamRepository;

    public SagaProjectionDataPortImpl(SagaEventStreamRepository eventStreamRepository) {
        this.eventStreamRepository = eventStreamRepository;
    }
    @Override
    public EventTransactionContext replayToCurrentState(UUID aggregationId) {

        // TODO SAGA VERSION TO CONTROL FLOW
        return this.eventStreamRepository.findByAggregationId(aggregationId).stream()
                .max(Comparator.comparing(EventStreamEntity::getVersion))
                .map(EventStreamEntity::toTransactionContext)
                .orElseThrow(() -> new RuntimeException("No events found for aggregationId: " + aggregationId));
    }
}
