package com.prado.tools.toolkitdev.eventsourcing.domain.factory;

import com.prado.tools.toolkitdev.eventsourcing.domain.service.commands.CalculateCommandTransaction;
import com.prado.tools.toolkitdev.eventsourcing.domain.service.commands.ChargeCommandTransaction;
import com.prado.tools.toolkitdev.eventsourcing.domain.service.commands.CommandTransaction;
import com.prado.tools.toolkitdev.eventsourcing.domain.service.commands.RegisterCommandTransaction;
import com.prado.tools.toolkitdev.eventsourcing.domain.vo.CommandType;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class SagaCommandTransactionFactory {

    private ApplicationContext applicationContext;

    public SagaCommandTransactionFactory(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }
    public CommandTransaction getCommandTransaction(CommandType commandType) {
        return switch (commandType) {
            case REGISTER -> this.applicationContext.getBean(RegisterCommandTransaction.class);
            case CALCULATE -> this.applicationContext.getBean(CalculateCommandTransaction.class);
            case CHARGE -> this.applicationContext.getBean(ChargeCommandTransaction.class);
        };
    }
}
