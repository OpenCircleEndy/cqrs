package com.ocs.cqrs.demo.programme;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@Slf4j
public class ProgrammeApi {

    @PostMapping("programme")
    void create(@RequestBody @Valid CreateProgrammeCommand command) {
        log.info("Received command {}", command);
    }
}
