package com.ocs.cqrs.demo.lead;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

/**
 * Handles the create Lead command.
 */
@Slf4j
@Service
class CreateLeadCommandHandler {

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    private LeadRepository repository;

    /**
     * Create Lead.
     *
     * @param createContractCommand create Lead command.
     * @return logical key of the created Lead.
     */
    String handle(CreateLeadCommand createContractCommand) {

        UUID customerEntityId = this.validateCustomer(createContractCommand.getCustomerId(), createContractCommand.getCustomerName());

        Lead newLead = this.store(new Lead(createContractCommand, customerEntityId));

        this.applicationEventPublisher.publishEvent(LeadCreated.builder().lead(newLead).build());

        return newLead.getNumber();
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

    private Lead store(Lead lead) {
        return this.repository.save(lead);
    }
}
