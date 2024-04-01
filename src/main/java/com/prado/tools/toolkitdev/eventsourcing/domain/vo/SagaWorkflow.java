package com.prado.tools.toolkitdev.eventsourcing.domain.vo;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class SagaWorkflow {

    private Long id;
    private String name;
    private LocalDateTime creationDate;
    private List<SagaWorkflowItem> sagaWorkflowItemList;

    public SagaWorkflow toVo(){
        return SagaWorkflow.builder()
                .id(id)
                .name(name)
                .creationDate(LocalDateTime.now())
                .sagaWorkflowItemList(sagaWorkflowItemList)
                .build();
    }

}
