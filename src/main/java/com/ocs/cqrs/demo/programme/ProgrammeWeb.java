package com.ocs.cqrs.demo.programme;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Builder(builderClassName = "ProgrammeWebBuilder")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@JsonDeserialize(builder = ProgrammeWeb.ProgrammeWebBuilder.class)
class ProgrammeWeb {

    @JsonProperty
    private final UUID id;
    @JsonProperty
    private final String code;
    @JsonProperty
    private final String customerName;

    static List<ProgrammeWeb> randomList() {
        return Arrays.asList(
                ProgrammeWeb.builder().id(UUID.randomUUID()).code("PRG-1").customerName("Vomar").build(),
                ProgrammeWeb.builder().id(UUID.randomUUID()).code("PRG-2").customerName("Jumbo").build(),
                ProgrammeWeb.builder().id(UUID.randomUUID()).code("PRG-3").customerName("Jan Linders").build());
    }

    @JsonPOJOBuilder(withPrefix = "")
    static class ProgrammeWebBuilder {
    }
}
