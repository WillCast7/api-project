package com.gvs.model.crm.repository;

import com.gvs.model.crm.entity.PermissionEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface PermissionRepository extends CrudRepository<PermissionEntity, Long> {

    @Query("SELECT pe.id, pe.name FROM PermissionEntity pe WHERE pe.name= :name")
    PermissionEntity findByName(String name);
}
