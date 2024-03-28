package com.prado.tools.toolkitdev.eventsourcing.domain.service.commands;

import com.prado.tools.toolkitdev.eventsourcing.domain.ports.calculation.CalculateUseCasePort;
import com.prado.tools.toolkitdev.eventsourcing.domain.vo.Event;
import com.prado.tools.toolkitdev.eventsourcing.domain.vo.EventTransactionContext;
import org.springframework.stereotype.Service;

@Service
public class CalculateCommandTransaction implements CommandTransaction {

    private CalculateUseCasePort calculateUseCasePort;

    public CalculateCommandTransaction(CalculateUseCasePort calculateUseCasePort) {
        this.calculateUseCasePort = calculateUseCasePort;
    }
    @Override
    public EventTransactionContext executeCommand(EventTransactionContext eventTransactionContext) {
        calculateUseCasePort.sendToCalculate(eventTransactionContext.getAggregationId());
        return eventTransactionContext;
    }
}
