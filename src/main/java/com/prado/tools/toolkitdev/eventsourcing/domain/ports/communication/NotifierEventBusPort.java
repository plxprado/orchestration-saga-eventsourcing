package com.prado.tools.toolkitdev.eventsourcing.domain.ports.communication;

import com.prado.tools.toolkitdev.eventsourcing.domain.vo.EventTransactionContext;


public interface NotifierEventBusPort {

    void nextCommand(EventTransactionContext eventTransactionContext);
}
