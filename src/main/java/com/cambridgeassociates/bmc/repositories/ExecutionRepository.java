package com.cambridgeassociates.bmc.repositories;

import com.cambridgeassociates.bmc.entities.Execution;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExecutionRepository extends CrudRepository<Execution, String> {
}
