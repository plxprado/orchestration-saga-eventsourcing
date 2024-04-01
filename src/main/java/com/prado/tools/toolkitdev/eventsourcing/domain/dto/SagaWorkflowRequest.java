package com.prado.tools.toolkitdev.eventsourcing.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.prado.tools.toolkitdev.eventsourcing.domain.vo.SagaWorkflow;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SagaWorkflowRequest {

    @JsonProperty("workflow_name")
    private String workflowName;

    public SagaWorkflow toVo() {
        return SagaWorkflow.builder()
                .name(workflowName)
                .creationDate(LocalDateTime.now())
                .build();
    }

}
