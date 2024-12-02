package com.aurealab.api.model.repository;

import com.aurealab.api.model.entity.PermissionEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PermissionRepository extends CrudRepository<PermissionEntity, Long> {

    @Query("SELECT pe.id, pe.name FROM PermissionEntity pe WHERE pe.name= :name")
    PermissionEntity findByName(String name);
}
