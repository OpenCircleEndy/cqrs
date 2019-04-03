package com.ocs.cqrs.demo.contract;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

/**
 * Handles the create contract command.
 */
@Slf4j
@Service
class CreateContractCommandHandler {

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    private ContractRepository repository;

    /**
     * Create contract.
     *
     * @param createContractCommand create contract command.
     * @return id of the created contract.
     */
    UUID handle(CreateContractCommand createContractCommand) {

        UUID customerEntityId = this.validateCustomer(createContractCommand.getCustomerId(), createContractCommand.getCustomerName());

        Contract newContract = this.store(new Contract(createContractCommand, customerEntityId));

        this.applicationEventPublisher.publishEvent(ContractCreated.builder().contract(newContract).build());

        return newContract.getId();
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

    private Contract store(Contract contract) {
        return this.repository.save(contract);
    }
}
