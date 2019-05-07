package com.ocs.cqrs.demo.relation;

import com.ocs.cqrs.demo.repository.ReadOnlyRepository;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

/**
 * In memory repository for {@link Relation}.
 */
@Repository
public class RelationRepository implements ReadOnlyRepository<Relation, UUID> {

    private Set<Relation> database = new HashSet<>();

    @Override
    public Optional<Relation> findById(UUID id) {
        return this.database.stream().filter(relation -> relation.getId().equals(id)).findAny();
    }

    @Override
    public boolean existsById(UUID id) {
        return this.findById(id).isPresent();
    }

    @Override
    public Iterable<Relation> findAll() {
        return this.database;
    }

    @Override
    public Iterable<Relation> findAllById(Iterable<UUID> uuids) {
        throw new UnsupportedOperationException("Find all by id is not supported");
    }

    @Override
    public long count() {
        return this.database.size();
    }

    @PostConstruct
    private void loadRelations() {
        this.database.add(Relation.builder().id(UUID.fromString("42fc4872-c358-4014-8e1d-eb786096ba87")).name("Acme Corporation").build());
        this.database.add(Relation.builder().id(UUID.fromString("15b48e3b-443e-40e0-b36f-43af14262aab")).name("Bubba Gump").build());
        this.database.add(Relation.builder().id(UUID.fromString("756c45ad-c0c3-4fa6-a9fe-2f4a0ee8fbf8")).name("Stark Industries").build());
        this.database.add(Relation.builder().id(UUID.fromString("7844a615-afd4-43f7-b56f-6bb50abb240d")).name("Umbrella Corporation").build());
        this.database.add(Relation.builder().id(UUID.fromString("ffd00cb2-72f6-4e5e-a03c-6966c244dc9c")).name("Wayne Enterprises").build());
    }
}
