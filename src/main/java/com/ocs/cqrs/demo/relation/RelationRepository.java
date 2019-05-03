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
        this.database.add(Relation.builder().id(UUID.randomUUID()).name("Acme Corporation").build());
        this.database.add(Relation.builder().id(UUID.randomUUID()).name("Globex Corporation").build());
        this.database.add(Relation.builder().id(UUID.randomUUID()).name("Soylent Corp").build());
        this.database.add(Relation.builder().id(UUID.randomUUID()).name("Initech").build());
        this.database.add(Relation.builder().id(UUID.randomUUID()).name("Bluth Company").build());
        this.database.add(Relation.builder().id(UUID.randomUUID()).name("Umbrella Corporation").build());
        this.database.add(Relation.builder().id(UUID.randomUUID()).name("Hooli").build());
        this.database.add(Relation.builder().id(UUID.randomUUID()).name("Vehement Capital Partners").build());
        this.database.add(Relation.builder().id(UUID.randomUUID()).name("Massive Dynamic").build());
        this.database.add(Relation.builder().id(UUID.randomUUID()).name("Wonka Industries").build());
        this.database.add(Relation.builder().id(UUID.randomUUID()).name("Stark Industries").build());
        this.database.add(Relation.builder().id(UUID.randomUUID()).name("Gekko & Co").build());
        this.database.add(Relation.builder().id(UUID.randomUUID()).name("Wayne Enterprises").build());
        this.database.add(Relation.builder().id(UUID.randomUUID()).name("Bubba Gump").build());
        this.database.add(Relation.builder().id(UUID.randomUUID()).name("Cyberdyne Systems").build());
        this.database.add(Relation.builder().id(UUID.randomUUID()).name("Genco Pura Olive Oil Company").build());
    }
}
