package com.gvs.controller;

import com.gvs.dto.APIResponseDTO;
import com.gvs.dto.CityDTO;
import com.gvs.service.DepartmentCityService;
import com.gvs.util.constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/city")
public class CityController {

    @Autowired
    DepartmentCityService departmentCityService;

    @GetMapping(produces = "application/json")
    public ResponseEntity<APIResponseDTO<List<CityDTO>>> getCitiesByDepartment( @RequestParam String departmentCode) {
        List<CityDTO> cityDTOSet = departmentCityService.getCitiesByDepartMent(departmentCode);
        if (cityDTOSet.isEmpty()){
            return  ResponseEntity.ofNullable(APIResponseDTO.failure(constants.messages.noData, constants.descriptions.empty));
        }
        return ResponseEntity.ok(APIResponseDTO.success(cityDTOSet, constants.messages.consultGood));
    }
}
