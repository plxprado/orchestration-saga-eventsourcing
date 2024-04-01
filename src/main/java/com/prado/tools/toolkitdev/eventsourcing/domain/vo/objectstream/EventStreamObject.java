package com.prado.tools.toolkitdev.eventsourcing.domain.vo.objectstream;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
@ToString
public class EventStreamObject implements Serializable {

    @JsonProperty("aggregationId")
    private UUID aggregationId;

    @JsonProperty("transactionExecutionDate")
    private LocalDateTime transactionExecutionDate;

    @JsonProperty("sagaName")
    private String sagaName;

    @JsonProperty("status")
    private String status;

    @JsonProperty("eventContentStreamObject")
    private EventContentStreamObject eventContentStreamObject;

    @JsonProperty("chargeStreamObject")
    private ChargeStreamObject chargeStreamObject;

    @JsonProperty("calculationStreamObject")
    private CalculationStreamObject calculationStreamObject;


}