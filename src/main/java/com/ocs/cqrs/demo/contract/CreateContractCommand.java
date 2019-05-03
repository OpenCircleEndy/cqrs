package com.ocs.cqrs.demo.contract;

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
class CreateContractCommand {

    @NotNull
    private String type;

    @NotNull
    private String leadNumber;
}
