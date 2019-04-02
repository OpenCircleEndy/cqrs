package com.ocs.cqrs.demo.contract;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

/**
 * Handles the create contract command.
 */
@Slf4j
@Service
class CreateContractCommandHandler {

    /**
     * Create contract.
     *
     * @param createContractCommand create contract command.
     * @return id of the created contract.
     */
    UUID handle(CreateContractCommand createContractCommand) {

        UUID customerEntityId = this.validateCustomer(createContractCommand.getCustomerId(), createContractCommand.getCustomerId());

        return this.store(new Contract(createContractCommand, customerEntityId));
    }

    private UUID validateCustomer(String customerId, String customerName) {
        if (!(Objects.isNull(customerId) || Objects.isNull(customerName))) {
            log.warn("CreateContract: Customer id and customer name present, customer id will be used");
            customerName = null;
        }

        if (customerId != null) {
            return this.getCustomerIdById(customerId);
        } else if (customerName != null) {
            return this.getCustomerIdByName(customerName);
        }
        return null;
    }

    private UUID getCustomerIdById(String customerId) {
        return UUID.fromString(customerId);
    }

    private UUID getCustomerIdByName(String customerName) {
        return UUID.randomUUID();
    }

    private UUID store(Contract contract) {
        return UUID.randomUUID();
    }
}
