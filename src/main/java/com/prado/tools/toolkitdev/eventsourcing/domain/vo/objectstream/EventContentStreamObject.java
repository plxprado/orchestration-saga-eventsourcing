package com.prado.tools.toolkitdev.eventsourcing.domain.vo.objectstream;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;


@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EventContentStreamObject implements java.io.Serializable{

    @JsonProperty("transactionValue")
    private BigDecimal transactionValue;

    @JsonProperty("externalTransactionId")
    private UUID externalTransactionId;

    @JsonProperty("transactionType")
    private String transactionType;

    @JsonProperty("transactionDate")
    private LocalDateTime transactionDate;



}
