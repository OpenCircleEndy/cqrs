package com.ocs.cqrs.demo.relation;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

/**
 * Relation domain object.
 */
@Builder
@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Relation {

    private UUID id;

    private String name;
}
