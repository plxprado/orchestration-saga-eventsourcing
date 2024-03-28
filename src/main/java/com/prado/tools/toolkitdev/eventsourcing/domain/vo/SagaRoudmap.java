package com.prado.tools.toolkitdev.eventsourcing.domain.vo;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class SagaRoudmap {

    private Long id;
    private CommandBusinessContext commandBusinessContext;
    private LocalDateTime creationDate;
    private List<SagaRoudmapItem> sagaRoudmapItemList;

    public SagaRoudmap toVo(){
        return SagaRoudmap.builder()
                .id(id)
                .commandBusinessContext(commandBusinessContext)
                .creationDate(LocalDateTime.now())
                .sagaRoudmapItemList(sagaRoudmapItemList)
                .build();
    }

}
