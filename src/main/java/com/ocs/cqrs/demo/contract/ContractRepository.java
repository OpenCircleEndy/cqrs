package com.ocs.cqrs.demo.contract;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ContractRepository extends CrudRepository<Contract, UUID> {
}
