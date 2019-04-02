package com.ocs.cqrs.demo.programme;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@SuppressWarnings("unused")
@RestController
@Slf4j
class ProgrammeApi {

    @PostMapping("programme")
    @ResponseStatus(HttpStatus.CREATED)
    void create(@RequestBody @Valid CreateProgrammeCommand command) {
        log.info("Received command {}", command);
    }

    @GetMapping("programme")
    List<ProgrammeWeb> list() {
        return ProgrammeWeb.randomList();
    }
}
