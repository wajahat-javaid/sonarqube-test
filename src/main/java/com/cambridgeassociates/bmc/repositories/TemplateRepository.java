package com.cambridgeassociates.bmc.repositories;

import com.cambridgeassociates.bmc.entities.Template;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TemplateRepository extends CrudRepository<Template, Integer> {

    Optional<Template> findByName(String name);
}
