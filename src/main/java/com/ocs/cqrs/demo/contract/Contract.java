package com.ocs.cqrs.demo.contract;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
class Contract {

    private UUID id;
    private String type;
    private UUID customerId;

    Contract(CreateContractCommand createContractCommand, UUID customerId) {
        this.id = UUID.randomUUID();
        this.type = createContractCommand.getType();
        this.customerId = customerId;
    }
}
