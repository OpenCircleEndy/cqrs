package com.ocs.cqrs.demo.lead;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Builder
@Data
@AllArgsConstructor
class Lead {
    private long id;

    private String number;

    private UUID customerId;

    private int quantity;

    Lead(CreateLeadCommand command, UUID customerId) {
        this.number = command.getNumber();
        this.customerId = customerId;
        this.quantity = command.getQuantity();
    }
}
