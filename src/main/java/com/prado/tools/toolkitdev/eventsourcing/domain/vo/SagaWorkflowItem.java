package com.prado.tools.toolkitdev.eventsourcing.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SagaWorkflowItem {

    private Long id;
    private StepCommandBusiness stepCommandBusiness;
    private Long stepOrder;
    private Boolean finalizer;
    private SagaWorkflow sagaWorkflow;

}
