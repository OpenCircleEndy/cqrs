package com.ocs.cqrs.demo.repository;


import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

import java.io.Serializable;
import java.util.Optional;

/**
 * Provide a read only repository for read-only views.
 *
 * @param <T>  the domain type the repository manages
 * @param <ID> the type of the id of the entity the repository manages
 */
@NoRepositoryBean
public interface ReadOnlyRepository<T, ID extends Serializable> extends Repository<T, ID> {

    /**
     * Retrieves an entity by its id.
     *
     * @param id must not be {@literal null}.
     * @return the entity with the given id or {@literal Optional#empty()} if none found
     * @throws IllegalArgumentException if {@code id} is {@literal null}.
     */
    Optional<T> findById(ID id);

    /**
     * Returns whether an entity with the given id exists.
     *
     * @param id must not be {@literal null}.
     * @return {@literal true} if an entity with the given id exists, {@literal false} otherwise.
     * @throws IllegalArgumentException if {@code id} is {@literal null}.
     */
    boolean existsById(ID id);

    /**
     * Returns all instances of the type.
     *
     * @return all entities
     */
    Iterable<T> findAll();

    /**
     * Returns all instances of the type with the given IDs.
     *
     * @param ids must not be {@literal null}.
     * @return all entities within the given IDs.
     */
    Iterable<T> findAllById(Iterable<ID> ids);

    /**
     * Returns the number of entities available.
     *
     * @return the number of entities
     */
    long count();
}
