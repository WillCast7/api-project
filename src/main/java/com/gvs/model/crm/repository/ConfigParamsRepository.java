package com.gvs.model.crm.repository;

import com.gvs.model.crm.entity.ConfigParamsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface ConfigParamsRepository extends JpaRepository<ConfigParamsEntity, Long> {
    Set<ConfigParamsEntity> findByParent(String name);
}
