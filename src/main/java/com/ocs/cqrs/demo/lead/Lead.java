package com.ocs.cqrs.demo.lead;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

/**
 * Lead domain object.
 */
@Builder
@Data
@AllArgsConstructor
public
class Lead {

    private long id;

    private String number;

    private UUID relationId;

    private int quantity;

    /**
     * Constructor creating a lead from a command.
     *
     * @param command    the command.
     * @param relationId the associated relation id.
     */
    Lead(CreateLeadCommand command, UUID relationId) {
        this.number = command.getNumber();
        this.relationId = relationId;
        this.quantity = command.getQuantity();
    }
}
