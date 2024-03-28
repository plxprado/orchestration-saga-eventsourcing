package com.prado.tools.toolkitdev.eventsourcing.rest;

import com.prado.tools.toolkitdev.eventsourcing.domain.service.TransctionOrchestrationManager;
import com.prado.tools.toolkitdev.eventsourcing.domain.dto.EventRequest;
import com.prado.tools.toolkitdev.eventsourcing.domain.vo.EventTransactionContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    private TransctionOrchestrationManager transctionOrchestrationManager;

    public TransactionController(TransctionOrchestrationManager transctionOrchestrationManager) {
        this.transctionOrchestrationManager = transctionOrchestrationManager;
    }

    @PostMapping("/charge")
    public ResponseEntity<EventTransactionContext> createTransaction(@RequestBody EventRequest eventRequest){
         return ResponseEntity.ok(transctionOrchestrationManager.initSagaTransaction(eventRequest));
    }



}
