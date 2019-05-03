package com.ocs.cqrs.demo.lead;

import com.ocs.cqrs.demo.relation.Relation;
import com.ocs.cqrs.demo.relation.RelationRepository;
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

    @Autowired
    private RelationRepository relationRepository;

    /**
     * Create Lead.
     *
     * @param createContractCommand create Lead command.
     * @return logical key of the created Lead.
     */
    String handle(CreateLeadCommand createContractCommand) {

        UUID relationEntityId = this.validateRelation(createContractCommand.getRelationId(), createContractCommand.getRelationName());

        Lead newLead = this.store(new Lead(createContractCommand, relationEntityId));

        this.applicationEventPublisher.publishEvent(LeadCreated.builder().lead(newLead).build());

        return newLead.getNumber();
    }

    private UUID validateRelation(String relationId, String relationName) {
        if (!(Objects.isNull(relationId) || Objects.isNull(relationName))) {
            log.warn("Create Lead: Relation id and relation name present, relation id will be used");
            relationName = null;
        }

        if (relationId != null) {
            return this.getRelationIdById(relationId);
        } else if (relationName != null) {
            throw new UnsupportedOperationException("Finding relations by name is not yet supported!");
        }
        return null;
    }

    private UUID getRelationIdById(String relationId) {
        return this.relationRepository.findById(UUID.fromString(relationId))
                .map(Relation::getId)
                .orElseThrow(IllegalStateException::new);
    }

    private Lead store(Lead lead) {
        return this.repository.save(lead);
    }
}
