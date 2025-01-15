package com.gvs.model.crm.repository;

import com.gvs.model.crm.entity.MenuItemEntity;
import com.gvs.model.crm.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.Set;

public interface MenuRepository extends JpaRepository<MenuItemEntity, Long> {

    @Query(value="select  menus.* from kaizen_menuroles menuroles " +
            "inner join kaizen_menus menus " +
            "on menus.id = menuroles.menu_id " +
            "inner join kaizen_roles roles " +
            "on roles.id = menuroles.role_id " +
            "where roles.validator = :role" , nativeQuery = true)
    Set<MenuItemEntity> findByRoleId(@Param("role") String role);

}
