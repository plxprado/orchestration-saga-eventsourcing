package com.prado.tools.toolkitdev.eventsourcing.domain.service.commands;

import com.prado.tools.toolkitdev.eventsourcing.domain.ports.persistence.SagaPersistencePort;
import com.prado.tools.toolkitdev.eventsourcing.domain.ports.stream.EventStreamAppenderPort;
import com.prado.tools.toolkitdev.eventsourcing.domain.service.orchestration.OrquestrationEventBusNotifier;
import com.prado.tools.toolkitdev.eventsourcing.domain.vo.EventTransactionContext;
import com.prado.tools.toolkitdev.eventsourcing.domain.vo.ProcessCommandStatus;
import com.prado.tools.toolkitdev.eventsourcing.persistence.repository.AggregationControllerRepository;
import com.prado.tools.toolkitdev.eventsourcing.persistence.repository.EventRepository;
import org.springframework.stereotype.Component;

@Component
public class RegisterCommandTransaction implements CommandTransaction {

    private SagaPersistencePort sagaPersistencePort;
    private AggregationControllerRepository aggregationControllerRepository;
    private EventRepository eventRepository;

    private OrquestrationEventBusNotifier orquestrationEventBusNotifier;

    private EventStreamAppenderPort eventStreamAppenderPort;


    public RegisterCommandTransaction(SagaPersistencePort sagaPersistencePort,
                                      AggregationControllerRepository aggregationControllerRepository,
                                      EventStreamAppenderPort eventStreamAppenderPort,

                                      EventRepository eventRepository,
                                      OrquestrationEventBusNotifier orquestrationEventBusNotifier) {

        this.sagaPersistencePort = sagaPersistencePort;
        this.aggregationControllerRepository = aggregationControllerRepository;
        this.eventRepository = eventRepository;
        this.eventStreamAppenderPort = eventStreamAppenderPort;
        this.orquestrationEventBusNotifier = orquestrationEventBusNotifier;
    }
    @Override
    public EventTransactionContext executeCommand(final EventTransactionContext eventTransactionContext) {

        final EventTransactionContext enventTransactionUpdated = EventTransactionContext.builder()
                .sagaRoudmapItem(eventTransactionContext.getSagaRoudmapItem())
                .aggregationId(eventTransactionContext.getAggregationId())
                .processCommandStatus(ProcessCommandStatus.COMPLETED)
                .build();

       eventStreamAppenderPort.appendToEventStream(enventTransactionUpdated);

        orquestrationEventBusNotifier.notifyOrchestrationHandler(enventTransactionUpdated);

        return enventTransactionUpdated;
    }
}
