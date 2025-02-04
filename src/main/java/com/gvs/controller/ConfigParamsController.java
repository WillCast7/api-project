package com.gvs.controller;

import com.gvs.dto.APIResponseDTO;
import com.gvs.dto.ConfigParamDTO;
import com.gvs.dto.DepartmentDTO;
import com.gvs.model.crm.entity.ConfigParamsEntity;
import com.gvs.model.sap.entity.CustomerDataViewEntity;
import com.gvs.model.sap.entity.CustomerTableEntity;
import com.gvs.service.ConfigParamService;
import com.gvs.service.CustomerService;
import com.gvs.service.DepartmentCityService;
import com.gvs.util.constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/configparams")
public class ConfigParamsController {

    @Autowired
    ConfigParamService configParamService;

    @Autowired
    DepartmentCityService departmentCityService;

    @Autowired
    CustomerService customerService;

    @GetMapping(produces = "application/json", value = "/crearacercamiento")
    public ResponseEntity<APIResponseDTO<Object>> getParams( @RequestParam String nit) {
        Set<ConfigParamDTO> contactResult = configParamService.getConfigParamsByParent("contactResult");
        Set<ConfigParamDTO> employeeNumber = configParamService.getConfigParamsByParent("employeeNumber");
        Set<ConfigParamDTO> positions = configParamService.getConfigParamsByParent("position");
        Set<ConfigParamDTO> numberTypes = configParamService.getConfigParamsByParent("numberType");
        List<DepartmentDTO> departmentSet = departmentCityService.getAllDepartments();
        CustomerDataViewEntity customerDataView = customerService.getCustomerDataView(nit);

        Map<String, Object> response = new HashMap<>();
        response.put("contactResult", contactResult);
        response.put("employeeNumber", employeeNumber);
        response.put("positions", positions);
        response.put("numberTypes", numberTypes);
        response.put("departments", departmentSet);
        response.put("customerDataView", customerDataView);
        return ResponseEntity.ok(APIResponseDTO.success(response, constants.success.findedSuccess ));
    }
}
