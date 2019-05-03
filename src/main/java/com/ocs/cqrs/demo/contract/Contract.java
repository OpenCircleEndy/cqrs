package com.ocs.cqrs.demo.contract;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

/**
 * Contract domain object.
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
class Contract {

    @Id
    private UUID id;
    private String type;

    @Column(name = "relation_id")
    private UUID relationId;

    /**
     * New contract can be created using the create contract command and a valid relation.
     *
     * @param createContractCommand the create contract command.
     * @param relationId            relation id.
     */
    Contract(CreateContractCommand createContractCommand, UUID relationId) {
        this.id = UUID.randomUUID();
        this.type = createContractCommand.getType();
        this.relationId = relationId;
    }
}
