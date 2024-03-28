package com.prado.tools.toolkitdev.eventsourcing.domain.service.aggregation;

import com.prado.tools.toolkitdev.eventsourcing.domain.ports.persistence.SagaPersistencePort;
import com.prado.tools.toolkitdev.eventsourcing.domain.vo.AggregationController;
import com.prado.tools.toolkitdev.eventsourcing.domain.vo.Event;
import org.springframework.stereotype.Service;

@Service
public class AggregationManagerService implements AggregationManager {

    private SagaPersistencePort sagaPersistencePort;

    public AggregationManagerService(final SagaPersistencePort sagaPersistencePort) {
        this.sagaPersistencePort = sagaPersistencePort;
    }
    @Override
    public AggregationController createAgregationToSaga(final Event event) {
        return sagaPersistencePort.createEventAgregration(event);
    }
}
