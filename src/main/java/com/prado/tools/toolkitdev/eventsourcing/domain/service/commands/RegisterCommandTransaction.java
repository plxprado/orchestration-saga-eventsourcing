package com.prado.tools.toolkitdev.eventsourcing.domain.service.commands;

import com.prado.tools.toolkitdev.eventsourcing.domain.ports.stream.EventStreamAppenderPort;
import com.prado.tools.toolkitdev.eventsourcing.domain.service.orchestration.OrquestrationEventBusNotifier;
import com.prado.tools.toolkitdev.eventsourcing.domain.vo.EventTransactionContext;
import com.prado.tools.toolkitdev.eventsourcing.domain.vo.ProcessCommandStatusEnum;
import com.prado.tools.toolkitdev.eventsourcing.domain.vo.objectstream.EventContentStreamObject;
import com.prado.tools.toolkitdev.eventsourcing.domain.vo.objectstream.EventStreamObject;
import com.prado.tools.toolkitdev.eventsourcing.persistence.entity.EventContentEntity;
import com.prado.tools.toolkitdev.eventsourcing.persistence.repository.EventRepository;
import org.springframework.stereotype.Component;

@Component
public class RegisterCommandTransaction implements CommandTransaction {


    private EventRepository eventRepository;
    private OrquestrationEventBusNotifier orquestrationEventBusNotifier;

    private EventStreamAppenderPort eventStreamAppenderPort;


    public RegisterCommandTransaction(final EventStreamAppenderPort eventStreamAppenderPort,
                                      final EventRepository eventRepository,
                                      final OrquestrationEventBusNotifier orquestrationEventBusNotifier) {


        this.eventRepository = eventRepository;
        this.eventStreamAppenderPort = eventStreamAppenderPort;
        this.orquestrationEventBusNotifier = orquestrationEventBusNotifier;
    }
    @Override
    public EventTransactionContext executeCommand(final EventTransactionContext eventTransactionContext) {

        final EventTransactionContext enventTransactionUpdated = eventTransactionContext.updateSagaProcessStatus(ProcessCommandStatusEnum.COMPLETED);

        final EventStreamObject currentEventStreamObject = enventTransactionUpdated.getEventStreamObject();
        final EventContentStreamObject eventStreamObject = currentEventStreamObject.getEventContentStreamObject();

        final EventContentEntity eventContentEntity = EventContentEntity.builder()
                .aggregationId(enventTransactionUpdated.getAggregationId())
                .transactionValue(eventStreamObject.getTransactionValue())
                .transactionDate(eventStreamObject.getTransactionDate())
                .transactionId(eventStreamObject.getExternalTransactionId())
                .type(eventStreamObject.getTransactionType())
                .build();

        eventRepository.save(eventContentEntity);

        eventStreamAppenderPort.appendToEventStream(enventTransactionUpdated);

        orquestrationEventBusNotifier.notifyOrchestrationHandler(enventTransactionUpdated);

        return enventTransactionUpdated;
    }
}
