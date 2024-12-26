package com.aurealab.api.model.repository;

import com.aurealab.api.model.entity.MenuItemEntity;
import org.springframework.data.repository.CrudRepository;

public interface MenuRepository extends CrudRepository<MenuItemEntity, Long> {
}
