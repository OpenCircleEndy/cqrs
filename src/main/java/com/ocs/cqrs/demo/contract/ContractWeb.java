package com.ocs.cqrs.demo.contract;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.UUID;

/**
 * Web app view of a contract.
 */
@Getter
@EqualsAndHashCode
@Builder(builderClassName = "ContractWebBuilder")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@JsonDeserialize(builder = ContractWeb.ContractWebBuilder.class)
class ContractWeb {

    @JsonProperty
    private final UUID id;

    @JsonProperty
    private final String type;

    @JsonProperty
    private final String relationName;

    @JsonPOJOBuilder
    static class ContractWebBuilder {
    }
}
