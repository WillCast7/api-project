package com.gvs.controller;

import com.gvs.dto.APIResponseDTO;
import com.gvs.dto.UserDTO;
import com.gvs.model.sap.entity.CustomerTableEntity;
import com.gvs.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/mercadeo")
public class customerController {

    @Autowired
    CustomerService customerService;

    @GetMapping(produces = "application/json", value = "/contactarclientes")
    public ResponseEntity<APIResponseDTO<Set<CustomerTableEntity>>> getUsers(@RequestParam(defaultValue = "10") int row,
                                                                        @RequestParam(defaultValue = "1") int page,
                                                                        @RequestParam(defaultValue = "") String searchValue) {
        System.out.println("entro");
        return customerService.getCustomersWithManualPagination(row, page, searchValue);
    }
}
