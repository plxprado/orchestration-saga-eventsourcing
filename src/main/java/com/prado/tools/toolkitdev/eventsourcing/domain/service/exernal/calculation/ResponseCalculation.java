package com.prado.tools.toolkitdev.eventsourcing.domain.service.exernal.calculation;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Builder
public class ResponseCalculation {

    private LocalDateTime calculationDate;
    private BigDecimal calculationResult;
    private String calculationStatus;
}
