package com.prado.tools.toolkitdev.eventsourcing.domain.service.aggregation;

import com.prado.tools.toolkitdev.eventsourcing.domain.ports.persistence.EventSourcingPersitencePort;
import com.prado.tools.toolkitdev.eventsourcing.domain.vo.AggregationController;
import com.prado.tools.toolkitdev.eventsourcing.domain.vo.Event;
import org.springframework.stereotype.Service;

@Service
public class AggregationManagerService implements AggregationManager {

    private EventSourcingPersitencePort eventSourcingPersitencePort;

    public AggregationManagerService(EventSourcingPersitencePort eventSourcingPersitencePort) {
        this.eventSourcingPersitencePort = eventSourcingPersitencePort;
    }
    @Override
    public AggregationController createAgregationToSaga(final Event event) {
        return this.eventSourcingPersitencePort.createEventAgregration(event);
    }
}
