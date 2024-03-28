package com.prado.tools.toolkitdev.eventsourcing.rest;

import com.prado.tools.toolkitdev.eventsourcing.domain.dto.CommandBusinessContextRequest;
import com.prado.tools.toolkitdev.eventsourcing.domain.dto.SagaRoudmapRequest;
import com.prado.tools.toolkitdev.eventsourcing.domain.ports.persistence.SagaPersistencePort;
import com.prado.tools.toolkitdev.eventsourcing.domain.vo.CommandBusinessContext;
import com.prado.tools.toolkitdev.eventsourcing.domain.vo.SagaRoudmap;
import com.prado.tools.toolkitdev.eventsourcing.domain.vo.SagaRoudmapItem;
import com.prado.tools.toolkitdev.eventsourcing.domain.vo.SagaRoudmapItemRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/saga")
public class SagaController {

    private SagaPersistencePort sagaPersistencePort;

    public SagaController(SagaPersistencePort sagaPersistencePort){
        this.sagaPersistencePort = sagaPersistencePort;
    }

    @PostMapping("/command/business-context")
    public ResponseEntity<CommandBusinessContext> createCommandBusinessContext(@RequestBody CommandBusinessContextRequest roudmapRequest){
        return ResponseEntity.ok(sagaPersistencePort.createCommandBusinessContext(roudmapRequest));
    }

    @PostMapping("/roudmap")
    public ResponseEntity<SagaRoudmap> createRoudmap(@RequestBody SagaRoudmapRequest sagaRoudmap){
        return ResponseEntity.ok(sagaPersistencePort.createSagaRoudmap(sagaRoudmap.toVo()));
    }

    @GetMapping("/roudmap")
    public ResponseEntity<List<SagaRoudmap>> getRoudmapItens(){
        return ResponseEntity.ok(sagaPersistencePort.allSagas());
    }

    @PostMapping("/roudmap/item/{id}")
    public ResponseEntity<SagaRoudmapItem> createSagaCicleItem(@PathVariable("id") Long idRoudmap,  @RequestBody SagaRoudmapItemRequest sagaItem){
        final SagaRoudmapItemRequest sagaItemRequest = SagaRoudmapItemRequest.builder()
                .sagaRoudmapId(idRoudmap)
                .stepOrder(sagaItem.getStepOrder())
                .stepName(sagaItem.getStepName())
                .finalizer(sagaItem.getFinalizer())
                .build();
        return ResponseEntity.ok(sagaPersistencePort.createSagaRoudmapItem(sagaItemRequest.toVo()));
    }
}
