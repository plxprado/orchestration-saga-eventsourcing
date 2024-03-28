package com.prado.tools.toolkitdev.eventsourcing.domain.service.commands;

import com.prado.tools.toolkitdev.eventsourcing.domain.vo.EventTransactionContext;

public interface CommandTransaction {

    EventTransactionContext executeCommand(EventTransactionContext eventTransactionContext);

}
