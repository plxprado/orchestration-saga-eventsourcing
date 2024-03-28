package com.prado.tools.toolkitdev.eventsourcing.domain.ports.calculation;

import com.prado.tools.toolkitdev.eventsourcing.domain.vo.Event;

import java.util.UUID;

public interface CalculateUseCasePort {
    void sendToCalculate(UUID aggregationId);
}
