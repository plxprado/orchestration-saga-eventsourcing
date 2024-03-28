package com.prado.tools.toolkitdev.eventsourcing.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Event implements java.io.Serializable {


    private UUID externalTransactionId;

    private String transactionType;

    private LocalDateTime transactionDate;

    private String transactionStatus;

    private BigDecimal transactionAmount;

    private CommandBusinessContext commandBusinessContext;
}
