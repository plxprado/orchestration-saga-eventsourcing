package com.prado.tools.toolkitdev.eventsourcing.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;


@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AggregationController {

    private UUID id;
    private UUID transactionId;
    private Long version;
    private String name;
    private LocalDateTime creationDate;
    private SagaWorkflow sagaWorkflow;
}

