package com.ocs.cqrs.demo.contract;

import com.ocs.cqrs.demo.relation.Relation;
import com.ocs.cqrs.demo.relation.RelationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * Contract projections for web view.
 */
@Repository
class ContractWebRepository {

    @Autowired
    private RelationRepository relationRepository;

    private Set<ContractWeb> contractProjections = new HashSet<>();

    List<ContractWeb> getAllContracts() {
        return new ArrayList<>(this.contractProjections);
    }

    @EventListener
    public void handleContractCreatedEvent(ContractCreated contractCreated) {
        String relationName = this.lookupRelationName(contractCreated.getContract().getRelationId());

        this.contractProjections.add(ContractWeb.builder()
                .id(contractCreated.getContract().getId())
                .type(contractCreated.getContract().getType())
                .relationName(relationName).build());
    }

    private String lookupRelationName(UUID relationId) {
        return this.relationRepository.findById(relationId).map(Relation::getName).orElseThrow(IllegalStateException::new);
    }
}
