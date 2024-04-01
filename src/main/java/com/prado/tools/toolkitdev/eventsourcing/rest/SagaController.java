package com.prado.tools.toolkitdev.eventsourcing.rest;

import com.prado.tools.toolkitdev.eventsourcing.domain.dto.ProcessCommandStatusRequest;
import com.prado.tools.toolkitdev.eventsourcing.domain.dto.SagaWorkflowRequest;
import com.prado.tools.toolkitdev.eventsourcing.domain.ports.persistence.SagaPersistencePort;
import com.prado.tools.toolkitdev.eventsourcing.domain.vo.ProcessCommandStatus;
import com.prado.tools.toolkitdev.eventsourcing.domain.vo.ProcessCommandStatusEnum;
import com.prado.tools.toolkitdev.eventsourcing.domain.vo.SagaWorkflowItemRequest;
import com.prado.tools.toolkitdev.eventsourcing.domain.vo.SagaWorkflow;
import com.prado.tools.toolkitdev.eventsourcing.domain.vo.SagaWorkflowItem;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/saga")
@CrossOrigin(origins = "*")
public class SagaController {

    private SagaPersistencePort sagaPersistencePort;

    public SagaController(SagaPersistencePort sagaPersistencePort){
        this.sagaPersistencePort = sagaPersistencePort;
    }


    @PostMapping("/workflow")
    public ResponseEntity<SagaWorkflow> createWorkflow(@RequestBody SagaWorkflowRequest sagaRoudmap){
        return ResponseEntity.ok(sagaPersistencePort.createSagaWorkflow(sagaRoudmap.toVo()));
    }

    @GetMapping("/workflow")
    public ResponseEntity<List<SagaWorkflow>> getWorkflowItems(){
        return ResponseEntity.ok(sagaPersistencePort.allSagas());
    }

    @PostMapping("/workflow/item/{id}")
    public ResponseEntity<SagaWorkflowItem> createWorkflowItem(@PathVariable("id") Long idRoudmap, @RequestBody SagaWorkflowItemRequest sagaItem){
        final SagaWorkflowItemRequest sagaItemRequest = SagaWorkflowItemRequest.builder()
                .sagaRoudmapId(idRoudmap)
                .stepOrder(sagaItem.getStepOrder())
                .stepName(sagaItem.getStepName())
                .finalizer(sagaItem.getFinalizer())
                .build();
        return ResponseEntity.ok(sagaPersistencePort.createSagaWorkflowItem(sagaItemRequest.toVo()));
    }

    @PostMapping("/workflow/process/status")
    public ResponseEntity<ProcessCommandStatus> getWorkflowProcessStatus(@RequestBody ProcessCommandStatusRequest processCommandStatusRequest){
        return ResponseEntity.ok(sagaPersistencePort.createProcessStatus(processCommandStatusRequest.toVo()));
    }

    @GetMapping("/workflow/process/status")
    public ResponseEntity<List<ProcessCommandStatus>> getWorkflowProcessStatus(){
        return ResponseEntity.ok(sagaPersistencePort.geallSagaWorkflowStatus());
    }
}
