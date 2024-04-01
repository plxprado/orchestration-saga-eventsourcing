package com.prado.tools.toolkitdev.eventsourcing.domain.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.prado.tools.toolkitdev.eventsourcing.domain.vo.StepCommandBusiness.valueOf;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SagaWorkflowItemRequest {

    @JsonIgnore
    private Long sagaRoudmapId;

    @JsonProperty("step_order")
    private Long stepOrder;

    @JsonProperty("step_name")
    private String stepName;

    @JsonProperty("finalizer")
    private Boolean finalizer;

    public SagaWorkflowItem toVo() {
        return SagaWorkflowItem.builder()
                .sagaWorkflow(SagaWorkflow.builder().id(sagaRoudmapId).build())
                .stepOrder(stepOrder)
                .stepCommandBusiness(valueOf(stepName))
                .finalizer(finalizer)
                .build();
    }

}
