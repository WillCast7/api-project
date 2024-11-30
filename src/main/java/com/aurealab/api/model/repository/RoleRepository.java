package com.aurealab.api.model.repository;

import com.aurealab.api.model.entity.PermissionEntity;
import com.aurealab.api.model.entity.RolesEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RoleRepository extends CrudRepository<RolesEntity, Long> {
    @Transactional
    @Query("SELECT re FROM RolesEntity re WHERE re.role = :role")
    RolesEntity findByName(String role);
}
