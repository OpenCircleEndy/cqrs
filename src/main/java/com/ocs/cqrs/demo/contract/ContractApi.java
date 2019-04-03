package com.ocs.cqrs.demo.contract;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * The combined command and query contract api.
 */
@SuppressWarnings("unused")
@RestController
@Slf4j
class ContractApi {

    /**
     * Command: Create a new contract.
     *
     * @param command create contract command.
     */
    @PostMapping("contracts")
    @ResponseStatus(HttpStatus.CREATED)
    void create(@RequestBody @Valid CreateContractCommand command) {
        log.info("Received command {}", command);
    }

    /**
     * Qeury: Get the list of contracts to display in a web application.
     *
     * @return web view of the list of contracts.
     */
    @CrossOrigin(origins = "*")
    @GetMapping("contracts")
    List<ContractWeb> list() {
        return ContractWeb.randomList();
    }
}
