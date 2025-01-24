package com.gvs.service.impl;

import com.gvs.model.crm.entity.MenuItemEntity;
import com.gvs.model.crm.repository.MenuRepository;
import com.gvs.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    MenuRepository menuRepository;

    @Transactional("crmTrxManager")
    public Set<MenuItemEntity> getMenuByRole(String role){
        return menuRepository.findByRoleId(role);
    }

}
