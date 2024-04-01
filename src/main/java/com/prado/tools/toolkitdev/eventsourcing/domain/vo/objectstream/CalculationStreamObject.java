package com.prado.tools.toolkitdev.eventsourcing.domain.vo.objectstream;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CalculationStreamObject implements java.io.Serializable  {

    @JsonProperty("transactionValueCalculated")
    private BigDecimal transactionValueCalculated;

}
