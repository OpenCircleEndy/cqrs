package com.ocs.cqrs.demo.lead;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * Jpa repository for {@link Lead}.
 */
@Repository
public class LeadRepository implements CrudRepository<Lead, Long> {

    private Set<Lead> database = new HashSet<>();

    @Override
    public <S extends Lead> S save(S s) {
        if (s.getId() < 1) {
            s.setId(this.generateId());
        }
        database.add(s);
        return s;
    }

    @Override
    public <S extends Lead> Iterable<S> saveAll(Iterable<S> iterable) {
        iterable.forEach(this::save);
        return iterable;
    }

    @Override
    public Optional<Lead> findById(Long id) {
        return this.database.stream()
                .filter(lead -> lead.getId() == id)
                .findAny();
    }

    @Override
    public boolean existsById(Long id) {
        return this.findById(id).isPresent();
    }

    @Override
    public Iterable<Lead> findAll() {
        return this.database;
    }

    @Override
    public Iterable<Lead> findAllById(Iterable<Long> iterable) {
        throw new UnsupportedOperationException();
    }

    @Override
    public long count() {
        return database.size();
    }

    @Override
    public void deleteById(Long aLong) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(Lead lead) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteAll(Iterable<? extends Lead> iterable) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteAll() {
        this.database = new HashSet<>();
    }


    private long generateId() {
        return this.database.stream()
                .map(Lead::getId)
                .mapToLong(id -> id)
                .max()
                .orElse(0L) + 1;
    }
}
