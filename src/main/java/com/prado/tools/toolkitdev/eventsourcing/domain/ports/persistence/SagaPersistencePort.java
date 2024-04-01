package com.prado.tools.toolkitdev.eventsourcing.domain.ports.persistence;

import com.prado.tools.toolkitdev.eventsourcing.domain.vo.ProcessCommandStatus;
import com.prado.tools.toolkitdev.eventsourcing.domain.vo.ProcessCommandStatusEnum;
import com.prado.tools.toolkitdev.eventsourcing.domain.vo.Event;
import com.prado.tools.toolkitdev.eventsourcing.domain.vo.SagaWorkflow;
import com.prado.tools.toolkitdev.eventsourcing.domain.vo.SagaWorkflowItem;
import com.prado.tools.toolkitdev.eventsourcing.domain.vo.SagaWorkflowIterator;

import java.util.List;
import java.util.UUID;

public interface SagaPersistencePort {


    SagaWorkflow createSagaWorkflow(SagaWorkflow sagaWorkflowRequest);

    List<SagaWorkflow> allSagas();

    SagaWorkflow searchSagaWorkflowByName(String sagaWorkflowName);

    SagaWorkflow sagaRoudmapById(Long id);

    SagaWorkflowIterator searchRoudmapIteratorById(final Long id);
    SagaWorkflowItem createSagaWorkflowItem(SagaWorkflowItem sagaWorkflowItemRequest);

    SagaWorkflowItem findSagaRoudmapItemById(Long id);

    Event loadEventByAggregationId(UUID aggregationId);

    ProcessCommandStatus createProcessStatus(ProcessCommandStatusEnum processCommandStatusEnum);

    List<ProcessCommandStatus> geallSagaWorkflowStatus();
}
