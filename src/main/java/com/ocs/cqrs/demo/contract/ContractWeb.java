package com.ocs.cqrs.demo.contract;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * Web app view of a contract.
 */
@Builder(builderClassName = "ContractWebBuilder")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@JsonDeserialize(builder = ContractWeb.ContractWebBuilder.class)
class ContractWeb {

    @JsonProperty
    private final String type;

    @JsonProperty
    private final UUID id;

    static List<ContractWeb> randomList() {
        return Arrays.asList(
                ContractWeb.builder().id(UUID.randomUUID()).type("CUSTOMER").customerName("ACME").build(),
                ContractWeb.builder().id(UUID.randomUUID()).type("SUPPLIER").customerName("OCP").build(),
                ContractWeb.builder().id(UUID.randomUUID()).type("CUSTOMER").customerName("Industry Coorporation Ltd.").build());
    }
    @JsonProperty
    private final String customerName;

    @JsonPOJOBuilder
    static class ContractWebBuilder {
    }
}
