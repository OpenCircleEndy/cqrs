package com.ocs.cqrs.demo.contract;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

/**
 * The combined command and query contract api.
 */
@SuppressWarnings("unused")
@RestController
@CrossOrigin(origins = "*")
@Slf4j
class ContractApi {

    @Autowired
    private CreateContractCommandHandler handler;

    @Autowired
    private ContractWebRepository repository;

    /**
     * Command: Create a new contract.
     *
     * @param command create contract command.
     */
    @PostMapping("contracts")
    @ResponseStatus(HttpStatus.CREATED)
    ResponseEntity<?> create(@RequestBody @Valid CreateContractCommand command) {
        return ResponseEntity.created(URI.create("contracts/" + this.handler.handle(command).toString())).build();
    }

    /**
     * Qeury: Get the list of contracts to display in a web application.
     *
     * @return web view of the list of contracts.
     */
    @GetMapping("contracts")
    List<ContractWeb> list() {
        return this.repository.getAllContracts();
    }
}
