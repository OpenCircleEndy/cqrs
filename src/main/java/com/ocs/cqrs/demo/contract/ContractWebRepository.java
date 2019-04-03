package com.ocs.cqrs.demo.contract;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * Contract projections for web view.
 */
@Repository
class ContractWebRepository {

    private static final Map<String, String> customerNames = new HashMap<>();

    static {
        customerNames.put("0", "Acme Corporation");
        customerNames.put("1", "Globex Corporation");
        customerNames.put("2", "Soylent Corp");
        customerNames.put("3", "Initech");
        customerNames.put("4", "Bluth Company");
        customerNames.put("5", "Umbrella Corporation");
        customerNames.put("6", "Hooli");
        customerNames.put("7", "Vehement Capital Partners");
        customerNames.put("8", "Massive Dynamic");
        customerNames.put("9", "Wonka Industries");
        customerNames.put("a", "Stark Industries");
        customerNames.put("b", "Gekko & Co");
        customerNames.put("c", "Wayne Enterprises");
        customerNames.put("d", "Bubba Gump");
        customerNames.put("e", "Cyberdyne Systems");
        customerNames.put("f", "Genco Pura Olive Oil Company");
    }

    private Set<ContractWeb> contractsPerCustomer = new HashSet<>();

    List<ContractWeb> getAllContracts() {
        return new ArrayList<>(this.contractsPerCustomer);
    }

    @EventListener
    public void handleContractCreatedEvent(ContractCreated contractCreated) {
        String customerName = this.lookupCustomerName(contractCreated.getContract().getCustomerId());

        this.contractsPerCustomer.add(ContractWeb.builder()
                .id(contractCreated.getContract().getId())
                .type(contractCreated.getContract().getType())
                .customerName(customerName).build());
    }

    private String lookupCustomerName(UUID customerId) {
        if (customerId != null) {
            return customerNames.get(customerId.toString().substring(0, 1));
        }
        return null;
    }
}
