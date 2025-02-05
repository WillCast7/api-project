package com.aurealab.model.emarket.repository;

import com.aurealab.model.emarket.entity.CityEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Set;

public interface CityRepository extends CrudRepository<CityEntity, Long> {
    List<CityEntity> findBydepartmentCodeOrderByName(String departamentCode);
}