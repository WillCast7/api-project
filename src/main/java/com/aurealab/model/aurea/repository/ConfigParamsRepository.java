package com.aurealab.model.aurea.repository;

import com.aurealab.model.aurea.entity.ConfigParamsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface ConfigParamsRepository extends JpaRepository<ConfigParamsEntity, Long> {
    Set<ConfigParamsEntity> findByParent(String name);
}
