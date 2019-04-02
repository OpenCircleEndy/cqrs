package com.ocs.cqrs.demo.programme;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@SuppressWarnings("unused")
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PRIVATE)
class CreateProgrammeCommand {

    @NotNull
    private String code;
    private String customerName;
    private String customerId;
}
