package com.ocs.cqrs.demo.contract;

import com.ocs.cqrs.demo.lead.Lead;
import com.ocs.cqrs.demo.lead.LeadRepository;
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
public class CreateContractCommandHandler {

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    private ContractRepository repository;

    @Autowired
    private LeadRepository leadRepository;

    /**
     * Create contract.
     *
     * @param createContractCommand create contract command.
     * @return id of the created contract.
     */
    public UUID handle(CreateContractCommand createContractCommand) {

        UUID relationEntityId = this.getRelationId(createContractCommand.getLeadNumber());

        Contract newContract = this.store(new Contract(createContractCommand, relationEntityId));

        this.applicationEventPublisher.publishEvent(ContractCreated.builder().contract(newContract).build());

        return newContract.getId();
    }

    private UUID getRelationId(String leadNumber) {
        if (Objects.isNull(leadNumber)) {
            throw new IllegalArgumentException("Lead Number not present!");
        }
        return this.leadRepository.findByNumber(leadNumber)
                .map(Lead::getRelationId)
                .orElseThrow(IllegalStateException::new);

    }

    private Contract store(Contract contract) {
        return this.repository.save(contract);
    }
}
