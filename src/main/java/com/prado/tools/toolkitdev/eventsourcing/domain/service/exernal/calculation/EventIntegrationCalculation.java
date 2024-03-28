package com.prado.tools.toolkitdev.eventsourcing.domain.service.exernal.calculation;

import com.prado.tools.toolkitdev.eventsourcing.domain.ports.persistence.SagaPersistencePort;
import com.prado.tools.toolkitdev.eventsourcing.domain.service.exernal.calculation.handler.EventCalculationSimulation;
import com.prado.tools.toolkitdev.eventsourcing.domain.vo.Event;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;

@Component
public class EventIntegrationCalculation {


    private static final int MIN_RANDOM = 10;
    private static final int MAX_RANDOM = 90;
    public ApplicationEventPublisher simulateEventsIntegration;

    private SagaPersistencePort sagaPersistencePort;

    public EventIntegrationCalculation(ApplicationEventPublisher simulateEventsIntegration, SagaPersistencePort sagaPersistencePort) {
        this.simulateEventsIntegration = simulateEventsIntegration;
        this.sagaPersistencePort = sagaPersistencePort;
    }


    public void calculate(final UUID aggregationId) {
       final Event eventContent = this.sagaPersistencePort.loadEventByAggregationId(aggregationId);
       final EventCalculationDTO eventCalculationDTO = EventCalculationDTO.builder()
                .aggregationId(aggregationId)
                .eventContent(eventContent.toString())
                .responseCalculation(createRandomResponse(eventContent))
                .build();
        this.simulateEventsIntegration.publishEvent(new EventCalculationSimulation(eventCalculationDTO));

    }

    private ResponseCalculation createRandomResponse(Event eventContent) {
        BigDecimal transactionValue = eventContent.getTransactionAmount();

        int tax = new Random().ints(MIN_RANDOM, MAX_RANDOM).findFirst().getAsInt();
        final BigDecimal valueToCharge = transactionValue.multiply(BigDecimal.valueOf(tax)).divide(BigDecimal.valueOf(100));


        return ResponseCalculation.builder()
                .calculationStatus("SUCESS")
                .calculationDate(LocalDateTime.now())
                .calculationResult(valueToCharge)
                .build();
    }


}
