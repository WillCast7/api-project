package com.gvs.service.impl;

import com.gvs.dto.CityDTO;
import com.gvs.dto.DepartmentDTO;
import com.gvs.model.emarket.entity.CityEntity;
import com.gvs.model.emarket.entity.DepartmentEntity;
import com.gvs.model.emarket.repository.CityRepository;
import com.gvs.model.emarket.repository.DepartmentRepository;
import com.gvs.service.DepartmentCityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class DepartmentCityServiceImpl implements DepartmentCityService {

    @Autowired
    DepartmentRepository departmentRepository;

    @Autowired
    CityRepository cityRepository;

    @Transactional("emarketTrxManager")
    public List<DepartmentDTO> getAllDepartments(){
        List<DepartmentEntity> departmentEntities = departmentRepository.findAllOrderByName();
        System.out.println(departmentEntities);
        if (departmentEntities.isEmpty()){return null;}
        List<DepartmentDTO> departmentDTOSet = new ArrayList<>();
        departmentEntities.stream().forEach(departmentItemEntity -> departmentDTOSet.add(setDepartmentEntityToDTO(departmentItemEntity)));

        return departmentDTOSet;

    }

    @Transactional("emarketTrxManager")
    public List<CityDTO> getCitiesByDepartMent(String codeDepartment){
        List<CityEntity> cityEntitiesSet = cityRepository.findBydepartmentCodeOrderByName(codeDepartment);
        if(cityEntitiesSet.isEmpty()){return null;}
        List<CityDTO> cityDTOSet = new ArrayList<>();
        cityEntitiesSet.stream().forEach(cityItemEntity -> cityDTOSet.add(setCityEntityToDTO(cityItemEntity)));

        return cityDTOSet;
    }

    public DepartmentDTO setDepartmentEntityToDTO(DepartmentEntity departmentEntity){
        return DepartmentDTO.builder()
                .name(departmentEntity.getName())
                .code(departmentEntity.getCode())
                .build();
    }

    public CityDTO setCityEntityToDTO(CityEntity cityEntity){
        return CityDTO.builder()
                .name(cityEntity.getName())
                .code(cityEntity.getCode())
                .build();
    }
}
