package com.aurealab.api.model.repository;

import com.aurealab.api.model.entity.PersonEntity;
import org.springframework.data.repository.CrudRepository;


public interface PersonRepository extends CrudRepository<PersonEntity, Long> {
}
