package com.ocs.cqrs.demo.lead;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotNull;

/**
 * Command requesting a new contract to be created.
 */
@SuppressWarnings("unused")
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PRIVATE)
class CreateLeadCommand {

    @NotNull
    private String number;

    private String customerName;

    private String customerId;

    private int quantity;
}
