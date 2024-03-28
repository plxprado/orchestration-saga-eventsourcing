package com.prado.tools.toolkitdev.eventsourcing.domain.ports.persistence;

import com.prado.tools.toolkitdev.eventsourcing.domain.dto.CommandBusinessContextRequest;
import com.prado.tools.toolkitdev.eventsourcing.domain.vo.AggregationController;
import com.prado.tools.toolkitdev.eventsourcing.domain.vo.CommandBusinessContext;
import com.prado.tools.toolkitdev.eventsourcing.domain.vo.Event;
import com.prado.tools.toolkitdev.eventsourcing.domain.vo.SagaRoudmap;
import com.prado.tools.toolkitdev.eventsourcing.domain.vo.SagaRoudmapItem;
import com.prado.tools.toolkitdev.eventsourcing.domain.vo.SagaRoudmapIterator;

import java.util.List;
import java.util.UUID;

public interface SagaPersistencePort {


    SagaRoudmap createSagaRoudmap(SagaRoudmap sagaRoudmapRequest);

    List<SagaRoudmap> allSagas();

    SagaRoudmap searchRoudmapByCommandBusinessContext(CommandBusinessContextRequest commandBusinessContext);

    SagaRoudmap sagaRoudmapById(Long id);

    SagaRoudmapIterator searchRoudmapIteratorById(final Long id);
    SagaRoudmapItem createSagaRoudmapItem(SagaRoudmapItem sagaRoudmapItemRequest);

    SagaRoudmapItem findSagaRoudmapItemById(Long id);
    AggregationController createEventAgregration(Event event);

    CommandBusinessContext searchByName(String name);

    SagaRoudmapIterator searchRoudmapByCommand(CommandBusinessContext commandBusinessContext);

    CommandBusinessContext createCommandBusinessContext(CommandBusinessContextRequest roudmapRequest);

    Event loadEventByAggregationId(UUID aggregationId);
}
