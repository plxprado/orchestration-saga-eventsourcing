package com.prado.tools.toolkitdev.eventsourcing.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SagaRoudmapItem {

    private Long id;
    private Long stepOrder;
    private String stepName;
    private Boolean finalizer;
    private SagaRoudmap sagaRoudmap;

}
