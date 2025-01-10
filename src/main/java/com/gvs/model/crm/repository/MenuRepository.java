package com.gvs.model.crm.repository;

import com.gvs.model.crm.entity.MenuItemEntity;
import org.springframework.data.repository.CrudRepository;

public interface MenuRepository extends CrudRepository<MenuItemEntity, Long> {
}
