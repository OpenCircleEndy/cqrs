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

    @Column(name = "customer_id")
    private UUID customerId;

    /**
     * New contract can be created using the create contract command and a valid customer.
     *
     * @param createContractCommand the create contract command.
     * @param customerId            customer id.
     */
    Contract(CreateContractCommand createContractCommand, UUID customerId) {
        this.id = UUID.randomUUID();
        this.type = createContractCommand.getType();
        this.customerId = customerId;
    }
}
