package com.prado.tools.toolkitdev.eventsourcing.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionJson {
    private BigDecimal transactionValueCalculated;
    private String status;
    private LocalDateTime transactionExecutionDate;
}