package com.prado.tools.toolkitdev.eventsourcing.domain.vo.objectstream;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ChargeStreamObject  implements java.io.Serializable{

    @JsonProperty("chargeType")
    private String chargeType;

    @JsonProperty("chargeStauts")
    private String chargeStauts;




}
