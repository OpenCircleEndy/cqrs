package com.ocs.cqrs.demo.programme;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@SuppressWarnings("unused")
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PRIVATE)
class CreateProgrammeCommand {
    private String code;
    private String retailerName;
    private String retailerId;
}
