package com.ocs.cqrs.demo.command;

import com.ocs.cqrs.demo.contract.CreateContractCommand;
import com.ocs.cqrs.demo.contract.CreateContractCommandHandler;
import com.ocs.cqrs.demo.lead.CreateLeadCommand;
import com.ocs.cqrs.demo.lead.CreateLeadCommandHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * Command Handler factory to allow extending the command handler chain.
 */
@Component
public class CommandHandler {

    @Autowired
    private ApplicationContext applicationContext;

    /**
     * Handle application domain command.
     *
     * @param command the command.
     */
    public void handle(Command command) {
        switch (command.getClass().getSimpleName()) {
            case "CreateContractCommand":
                this.handle((CreateContractCommand) command);
                break;
            case "CreateLeadCommand":
                this.handle((CreateLeadCommand) command);
                break;
            default:
                throw new UnsupportedOperationException("Command is not supported: " + command.getClass().getSimpleName());
        }
    }

    private void handle(CreateContractCommand command) {
        this.applicationContext.getBean(CreateContractCommandHandler.class).handle(command);
    }

    private void handle(CreateLeadCommand command) {
        this.applicationContext.getBean(CreateLeadCommandHandler.class).handle(command);
    }
}
