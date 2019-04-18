package com.ocs.cqrs.demo.lead;


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
class LeadCreated {
    private final Lead lead;
}
