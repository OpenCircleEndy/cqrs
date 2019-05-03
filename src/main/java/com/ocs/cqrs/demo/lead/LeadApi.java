package com.ocs.cqrs.demo.lead;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;

/**
 * The combined command and query lead api.
 */
@SuppressWarnings("unused")
@RestController
@CrossOrigin(origins = "*")
@Slf4j
class LeadApi {

    @Autowired
    private CreateLeadCommandHandler handler;

    @Autowired
    private LeadRepository repository;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    /**
     * Command: Create a new lead.
     *
     * @param command create lead command.
     * @return HTTP Status Code 201, if the the create lead command is processed.
     */
    @PostMapping("leads")
    @ResponseStatus(HttpStatus.CREATED)
    ResponseEntity<?> create(@RequestBody @Valid CreateLeadCommand command) {
        return ResponseEntity.created(URI.create("leads/" + this.handler.handle(command))).build();
    }

    /**
     * Query: Get the list of leads to display in a web application.
     *
     * @param leadCreated lead created event.
     */
    @EventListener
    public void handleLeadCreatedEvent(LeadCreated leadCreated) {
        this.simpMessagingTemplate.convertAndSend("/topic/leads", leadCreated.getLead());
    }
}
