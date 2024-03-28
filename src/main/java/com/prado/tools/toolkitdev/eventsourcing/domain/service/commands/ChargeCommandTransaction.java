package com.prado.tools.toolkitdev.eventsourcing.domain.service.commands;

import com.prado.tools.toolkitdev.eventsourcing.domain.vo.EventTransactionContext;

public class ChargeCommandTransaction implements CommandTransaction {
    @Override
    public EventTransactionContext executeCommand(EventTransactionContext eventTransactionContext) {
        return null;
    }
}
