package com.ocs.cqrs.demo.lead;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * In memory repository for {@link Lead}.
 */
@Repository
public class LeadRepository implements CrudRepository<Lead, Long> {

    private Set<Lead> database = new HashSet<>();

    @Override
    public Lead save(@NotNull Lead lead) {
        if (lead.getId() < 1) {
            lead.setId(this.generateId());
        }
        database.add(lead);
        return lead;
    }

    @Override
    public <S extends Lead> Iterable<S> saveAll(Iterable<S> iterable) {
        iterable.forEach(this::save);
        return iterable;
    }

    @Override
    public Optional<Lead> findById(@NotNull Long id) {
        return this.database.stream()
                .filter(lead -> lead.getId() == id)
                .findAny();
    }

    @Override
    public boolean existsById(@NotNull Long id) {
        return this.findById(id).isPresent();
    }

    @Override
    public Iterable<Lead> findAll() {
        return this.database;
    }

    @Override
    public Iterable<Lead> findAllById(Iterable<Long> iterable) {
        throw new UnsupportedOperationException("Find all by id is not supported");
    }

    /**
     * Find by lead number.
     *
     * @param number must not be {@literal null}.
     * @return the entity with the given id or {@literal Optional#empty()} if none found
     * @throws IllegalArgumentException if {@code number} is {@literal null}.
     */
    public Optional<Lead> findByNumber(@NotNull String number) {
        return this.database.stream()
                .filter(lead -> lead.getNumber().equals(number))
                .findAny();
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
