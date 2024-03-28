package com.prado.tools.toolkitdev.eventsourcing.domain.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SagaRoudmapItemRequest {

    @JsonIgnore
    private Long sagaRoudmapId;

    @JsonProperty("step_order")
    private Long stepOrder;

    @JsonProperty("step_name")
    private String stepName;

    @JsonProperty("finalizer")
    private Boolean finalizer;

    public SagaRoudmapItem toVo(){
        return SagaRoudmapItem.builder()
                .sagaRoudmap(SagaRoudmap.builder().id(sagaRoudmapId).build())
                .stepOrder(stepOrder)
                .stepName(stepName)
                .finalizer(finalizer)
                .build();
    }

}
