package com.gvs.service;

import com.gvs.dto.CityDTO;
import com.gvs.dto.DepartmentDTO;

import java.util.List;
import java.util.Set;

public interface DepartmentCityService {
    public List<DepartmentDTO> getAllDepartments();
    public List<CityDTO> getCitiesByDepartMent(String codeDepartment);
}
