package com.ocs.cqrs.demo.relation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Relation command and query API for web app.
 */
@SuppressWarnings("unused")
@RestController
@CrossOrigin(origins = "*")
@Slf4j
class RelationApi {

    @Autowired
    private RelationRepository repository;

    /**
     * Query: Get all relations.
     *
     * @return iterable collection of relations.
     */
    @GetMapping("relations")
    Iterable<Relation> getAllRelations() {
        return this.repository.findAll();
    }
}
