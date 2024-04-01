package com.prado.tools.toolkitdev.eventsourcing.adapter;

import com.prado.tools.toolkitdev.eventsourcing.domain.ports.persistence.SagaPersistencePort;
import com.prado.tools.toolkitdev.eventsourcing.domain.vo.Event;
import com.prado.tools.toolkitdev.eventsourcing.domain.vo.ProcessCommandStatus;
import com.prado.tools.toolkitdev.eventsourcing.domain.vo.ProcessCommandStatusEnum;
import com.prado.tools.toolkitdev.eventsourcing.domain.vo.SagaWorkflow;
import com.prado.tools.toolkitdev.eventsourcing.domain.vo.SagaWorkflowItem;
import com.prado.tools.toolkitdev.eventsourcing.domain.vo.SagaWorkflowIterator;
import com.prado.tools.toolkitdev.eventsourcing.persistence.entity.ProcessStatusEntity;
import com.prado.tools.toolkitdev.eventsourcing.persistence.entity.SagaWorkflowEntity;
import com.prado.tools.toolkitdev.eventsourcing.persistence.entity.SagaWorkflowItemEntity;
import com.prado.tools.toolkitdev.eventsourcing.persistence.repository.EventRepository;
import com.prado.tools.toolkitdev.eventsourcing.persistence.repository.ProcessStatusRepository;
import com.prado.tools.toolkitdev.eventsourcing.persistence.repository.SagaWorkflowItemRepository;
import com.prado.tools.toolkitdev.eventsourcing.persistence.repository.SagaWorkflowRepository;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class SagaPersistenceAdapter implements SagaPersistencePort {

    private static final Long FIRST_VERSION = 1L;

    private SagaWorkflowRepository sagaWorkflowRepository;

    private SagaWorkflowItemRepository sagaWorkflowItemRepository;

    private EventRepository eventRepository;

    private ProcessStatusRepository processStatusRepository;



    public SagaPersistenceAdapter(SagaWorkflowRepository sagaWorkflowRepository,
                                  SagaWorkflowItemRepository sagaWorkflowItemRepository,
                                  EventRepository eventRepository,
                                  ProcessStatusRepository processStatusRepository) {
        this.sagaWorkflowRepository = sagaWorkflowRepository;
        this.sagaWorkflowItemRepository = sagaWorkflowItemRepository;
        this.eventRepository = eventRepository;
        this.processStatusRepository = processStatusRepository;
    }

    @Override
    public SagaWorkflow createSagaWorkflow(SagaWorkflow sagaWorkflow) {
        return this.sagaWorkflowRepository.save(SagaWorkflowEntity.builder()
                .name(sagaWorkflow.getName())
                .creationDate(sagaWorkflow.getCreationDate())
                .build()).toVO();

    }

    @Override
    public List<SagaWorkflow> allSagas() {
        return sagaWorkflowRepository.loadAll().stream().map(SagaWorkflowEntity::toVOWithItems).collect(Collectors.toList());
    }

    @Override
    public SagaWorkflow searchSagaWorkflowByName(final String sagaWorkflowName) {
        return this.sagaWorkflowRepository.findByName(sagaWorkflowName)
                .orElseThrow(() -> new RuntimeException("No saga found"))
                .toVOWithItems();

    }

    @Override
    public SagaWorkflow sagaRoudmapById(Long id) {
        return this.sagaWorkflowRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No saga found"))
                .toVO();
    }

    @Override
    public SagaWorkflowItem createSagaWorkflowItem(SagaWorkflowItem sagaWorkflowItem) throws ConstraintViolationException {
        final SagaWorkflowEntity sagaWorkflowEntity = sagaWorkflowRepository.findById(sagaWorkflowItem.getSagaWorkflow().getId())
                .orElseThrow(() -> new RuntimeException("No saga found"));

        SagaWorkflowItemEntity sagaWorkflowItemEntity = SagaWorkflowItemEntity.builder()
                .stepOrder(sagaWorkflowItem.getStepOrder())
                .stepCommandBusiness(sagaWorkflowItem.getStepCommandBusiness())
                .finalizer(sagaWorkflowItem.getFinalizer())
                .sagaRoudmap(sagaWorkflowEntity)
                .build();
        return this.sagaWorkflowItemRepository.save(sagaWorkflowItemEntity).toVO();



    }

    @Override
    public SagaWorkflowItem findSagaRoudmapItemById(Long id) {
        return this.sagaWorkflowItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No saga found"))
                .toVO();
    }

    @Override
    public Event loadEventByAggregationId(UUID aggregationId) {
        return this.eventRepository.findByAggregationId(aggregationId)
                .orElseThrow(() -> new RuntimeException("No event found"))
                .toVo();
    }

    @Override
    public ProcessCommandStatus createProcessStatus(ProcessCommandStatusEnum processCommandStatusEnum) {
        return this.processStatusRepository.save(ProcessStatusEntity.builder()
                        .name(processCommandStatusEnum.name())
                .build()).toVo();
    }

    @Override
    public List<ProcessCommandStatus> geallSagaWorkflowStatus() {
        return  StreamSupport
                .stream(this.processStatusRepository.findAll().spliterator(), false)
                .map(ProcessStatusEntity::toVo)
                .collect(Collectors.toList());

    }


    public SagaWorkflowIterator searchRoudmapIteratorById(final Long id) {
        SagaWorkflowEntity sagaWorkflowEntity = this.sagaWorkflowRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No saga found"));

        final List<SagaWorkflowItem> items = sagaWorkflowEntity.getItems()
                .stream()
                .map(SagaWorkflowItemEntity::toVO)
                .sorted(Comparator.comparing(SagaWorkflowItem::getStepOrder))
                .toList();

        return new SagaWorkflowIterator(items);
    }





}
