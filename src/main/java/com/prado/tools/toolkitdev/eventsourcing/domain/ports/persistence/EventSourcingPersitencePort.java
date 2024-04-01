package com.prado.tools.toolkitdev.eventsourcing.domain.ports.persistence;

import com.prado.tools.toolkitdev.eventsourcing.domain.vo.AggregationController;
import com.prado.tools.toolkitdev.eventsourcing.domain.vo.Event;

public interface EventSourcingPersitencePort {

    AggregationController createEventAgregration(Event event);

}
