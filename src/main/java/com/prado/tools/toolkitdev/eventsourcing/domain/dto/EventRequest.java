package com.prado.tools.toolkitdev.eventsourcing.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.prado.tools.toolkitdev.eventsourcing.domain.vo.CommandBusinessContext;
import com.prado.tools.toolkitdev.eventsourcing.domain.vo.Event;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;


public record EventRequest(@JsonProperty("transaction_id") UUID transactionId,
                           @JsonProperty("transaction_value") BigDecimal transactionValue,
                           @JsonProperty("transaction_date") LocalDateTime transactionDate,
                           @JsonProperty("command_business") String commandBusiness) {

    public Event toVo() {
        return Event.builder()
                .transactionAmount(transactionValue())
                .transactionDate(transactionDate())
                .transactionType(commandBusiness())
                .externalTransactionId(transactionId())
                .commandBusinessContext(new CommandBusinessContext(null, commandBusiness()))
                .build();
    }
}
