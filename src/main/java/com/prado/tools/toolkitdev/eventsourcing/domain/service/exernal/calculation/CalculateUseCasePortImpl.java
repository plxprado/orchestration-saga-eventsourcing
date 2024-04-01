package com.prado.tools.toolkitdev.eventsourcing.domain.service.exernal.calculation;

import com.prado.tools.toolkitdev.eventsourcing.domain.ports.calculation.CalculateUseCasePort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CalculateUseCasePortImpl implements CalculateUseCasePort {


    private EventIntegrationCalculation eventIntegrationCalculation;

    public CalculateUseCasePortImpl(EventIntegrationCalculation eventIntegrationCalculation) {
        this.eventIntegrationCalculation = eventIntegrationCalculation;
    }
    @Override
    public void sendToCalculate(UUID aggregationId) {
        eventIntegrationCalculation.calculate(aggregationId);
    }
}
