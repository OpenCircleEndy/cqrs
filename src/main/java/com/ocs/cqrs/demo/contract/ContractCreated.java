package com.ocs.cqrs.demo.contract;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * Contract created event.
 */
@Builder
@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
class ContractCreated {
    private final Contract contract;
}
