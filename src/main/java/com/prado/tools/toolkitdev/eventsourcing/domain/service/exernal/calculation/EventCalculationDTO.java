package com.prado.tools.toolkitdev.eventsourcing.domain.service.exernal.calculation;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class EventCalculationDTO {

    private UUID aggregationId;
    private String eventContent;

    private ResponseCalculation responseCalculation;
}
