package com.prado.tools.toolkitdev.eventsourcing.domain.vo;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Builder
@Getter
public class EventTransactionContext {

    private UUID aggregationId;
    private SagaRoudmapItem sagaRoudmapItem;
    private ProcessCommandStatus processCommandStatus;
    private TransactionJson transactionJson;


}
