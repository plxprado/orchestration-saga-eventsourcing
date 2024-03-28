package com.prado.tools.toolkitdev.eventsourcing.adapter;

import com.prado.tools.toolkitdev.eventsourcing.domain.dto.CommandBusinessContextRequest;
import com.prado.tools.toolkitdev.eventsourcing.domain.ports.persistence.SagaPersistencePort;
import com.prado.tools.toolkitdev.eventsourcing.domain.vo.AggregationController;
import com.prado.tools.toolkitdev.eventsourcing.domain.vo.CommandBusinessContext;
import com.prado.tools.toolkitdev.eventsourcing.domain.vo.Event;
import com.prado.tools.toolkitdev.eventsourcing.domain.vo.SagaRoudmap;
import com.prado.tools.toolkitdev.eventsourcing.domain.vo.SagaRoudmapItem;
import com.prado.tools.toolkitdev.eventsourcing.domain.vo.SagaRoudmapIterator;
import com.prado.tools.toolkitdev.eventsourcing.persistence.entity.AggregationControllerEntity;
import com.prado.tools.toolkitdev.eventsourcing.persistence.entity.CommandBusinessContextEntity;
import com.prado.tools.toolkitdev.eventsourcing.persistence.entity.SagaRoudmapEntity;
import com.prado.tools.toolkitdev.eventsourcing.persistence.entity.SagaRoudmapItemEntity;
import com.prado.tools.toolkitdev.eventsourcing.persistence.repository.AggregationControllerRepository;
import com.prado.tools.toolkitdev.eventsourcing.persistence.repository.CommandBusinessContextRepository;
import com.prado.tools.toolkitdev.eventsourcing.persistence.repository.EventRepository;
import com.prado.tools.toolkitdev.eventsourcing.persistence.repository.SagaRoudmapItemRepository;
import com.prado.tools.toolkitdev.eventsourcing.persistence.repository.SagaRoudmapRepository;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class SagaPersistenceAdapter implements SagaPersistencePort {

    private static final Long FIRST_VERSION = 1L;

    private SagaRoudmapRepository sagaRoudmapRepository;

    private SagaRoudmapItemRepository sagaRoudmapItemRepository;

    private AggregationControllerRepository aggregationControllerRepository;

    private CommandBusinessContextRepository commandBusinessContextRepository;

    private EventRepository eventRepository;

    public SagaPersistenceAdapter(SagaRoudmapRepository sagaRoudmapRepository,
                                  SagaRoudmapItemRepository sagaRoudmapItemRepository,
                                  AggregationControllerRepository aggregationControllerRepository,
                                  CommandBusinessContextRepository commandBusinessContextRepository,
                                  EventRepository eventRepository) {
        this.sagaRoudmapRepository = sagaRoudmapRepository;
        this.sagaRoudmapItemRepository = sagaRoudmapItemRepository;
        this.aggregationControllerRepository = aggregationControllerRepository;
        this.commandBusinessContextRepository = commandBusinessContextRepository;
        this.eventRepository = eventRepository;
    }


    @Override
    public SagaRoudmap createSagaRoudmap(SagaRoudmap sagaRoudmap) {
        CommandBusinessContextEntity commandBusinessContextEntity = commandBusinessContextRepository.findByName(sagaRoudmap.getCommandBusinessContext().getName())
                .orElseThrow(() -> new RuntimeException("No command business context found"));
        return this.sagaRoudmapRepository.save(SagaRoudmapEntity.builder()
                .commandBusinessContext(commandBusinessContextEntity)
                .creationDate(sagaRoudmap.getCreationDate())
                .build()).toVO();

    }

    @Override
    public List<SagaRoudmap> allSagas() {
        return sagaRoudmapRepository.loadAll().stream().map(SagaRoudmapEntity::toVOWithList).collect(Collectors.toList());
    }

    @Override
    public SagaRoudmap searchRoudmapByCommandBusinessContext(CommandBusinessContextRequest commandBusinessContext) {
        CommandBusinessContextEntity commandBusinessContextEntity = commandBusinessContextRepository.findByName(commandBusinessContext.getCommandName())
                .orElseThrow(() -> new RuntimeException("No command business context found"));

        return this.sagaRoudmapRepository.findByCommandBusinessContextId(commandBusinessContextEntity.getId())
                .orElseThrow(() -> new RuntimeException("No saga found"))
                .toVO();

    }

    @Override
    public SagaRoudmap sagaRoudmapById(Long id) {
        return this.sagaRoudmapRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No saga found"))
                .toVO();
    }

    @Override
    public SagaRoudmapItem createSagaRoudmapItem(SagaRoudmapItem sagaRoudmapItem) throws ConstraintViolationException {
        final SagaRoudmapEntity sagaRoudmapEntity = sagaRoudmapRepository.findById(sagaRoudmapItem.getSagaRoudmap().getId())
                .orElseThrow(() -> new RuntimeException("No saga found"));

        SagaRoudmapItemEntity sagaRoudmapItemEntity = SagaRoudmapItemEntity.builder()
                .stepOrder(sagaRoudmapItem.getStepOrder())
                .stepName(sagaRoudmapItem.getStepName())
                .finalizer(sagaRoudmapItem.getFinalizer())
                .sagaRoudmap(sagaRoudmapEntity)
                .build();
        return this.sagaRoudmapItemRepository.save(sagaRoudmapItemEntity).toVO();



    }

    @Override
    public SagaRoudmapItem findSagaRoudmapItemById(Long id) {
        return this.sagaRoudmapItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No saga found"))
                .toVO();
    }

    @Override
    public AggregationController createEventAgregration(Event event) throws DuplicateKeyException {

        final CommandBusinessContextEntity commandBusinessContextEntity =
                this.commandBusinessContextRepository.findByName(event.getTransactionType())
                        .orElseThrow(() -> new RuntimeException("No command business context found"));

        final AggregationControllerEntity aggregationEntity = AggregationControllerEntity.builder()
                .commandBusinessContext(commandBusinessContextEntity)
                .transactionId(event.getExternalTransactionId())
                .creationDate(LocalDateTime.now())
                .version(FIRST_VERSION)
                .build();

        AggregationControllerEntity aggregationController = aggregationControllerRepository.save(aggregationEntity);

        return aggregationController.toVO();
    }

    @Override
    public CommandBusinessContext searchByName(String name) {
        return this.commandBusinessContextRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("No command business context found"))
                .toVO();
    }

    @Override
    public SagaRoudmapIterator searchRoudmapByCommand(CommandBusinessContext commandBusinessContext) {
        SagaRoudmapEntity sagaRoudmapEntity = this.sagaRoudmapRepository.findByCommandBusinessName(commandBusinessContext.getName())
                .orElseThrow(() -> new RuntimeException("No saga found"));

        final List<SagaRoudmapItem> items = sagaRoudmapEntity.getItems()
                .stream()
                .map(SagaRoudmapItemEntity::toVO)
                .sorted(Comparator.comparing(SagaRoudmapItem::getStepOrder))
                .toList();

        return new SagaRoudmapIterator(items);
    }

    public SagaRoudmapIterator searchRoudmapIteratorById(final Long id) {
        SagaRoudmapEntity sagaRoudmapEntity = this.sagaRoudmapRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No saga found"));

        final List<SagaRoudmapItem> items = sagaRoudmapEntity.getItems()
                .stream()
                .map(SagaRoudmapItemEntity::toVO)
                .sorted(Comparator.comparing(SagaRoudmapItem::getStepOrder))
                .toList();

        return new SagaRoudmapIterator(items);
    }

    @Override
    public CommandBusinessContext createCommandBusinessContext(CommandBusinessContextRequest roudmapRequest) {
        return this.commandBusinessContextRepository.save(CommandBusinessContextEntity.builder()
                .creationDate(LocalDateTime.now())
                .name(roudmapRequest.getCommandName())
                .build()).toVO();
    }

    @Override
    //TODO estrutura de cache
    public Event loadEventByAggregationId(UUID aggregationId) {
        return this.eventRepository.findByAggregationId(aggregationId)
                .orElseThrow(() -> new RuntimeException("No event found"))
                .toVo();
    }


}
