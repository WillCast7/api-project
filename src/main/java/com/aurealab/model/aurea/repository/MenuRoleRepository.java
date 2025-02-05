package com.aurealab.api.model.repository;

import com.aurealab.api.model.entity.MenuItemEntity;
import com.aurealab.api.model.entity.MenuRoleEntity;
import org.springframework.data.repository.CrudRepository;

public interface MenuRoleRepository extends CrudRepository<MenuRoleEntity, Long> {
}
