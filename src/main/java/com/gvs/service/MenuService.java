package com.gvs.service;

import com.gvs.model.crm.entity.MenuItemEntity;

import java.util.Optional;
import java.util.Set;

public interface MenuService {
    public Set<MenuItemEntity> getMenuByRole(String role);
}
