package com.gvs.model.crm.repository;

import com.gvs.model.b1.entity.UserEntity;
import com.gvs.model.crm.entity.RoleEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RoleRepository extends CrudRepository<RoleEntity, Long> {

    @Query("SELECT re FROM RoleEntity re WHERE re.role = :role")
    Optional<RoleEntity> findByName(String role);


    Optional<RoleEntity> findByValidator(@Param("validator") String role);

}
