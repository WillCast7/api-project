package com.aurealab.controller;

import com.aurealab.dto.APIResponseDTO;
import com.aurealab.model.sap.entity.CustomerTableEntity;
import com.aurealab.service.CustomerService;
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
                                                                        @RequestParam(defaultValue = "") String searchValue,
                                                                         @RequestParam(defaultValue = "todos") String selectedFilter
                                                                             ) {
        return ResponseEntity.ok(APIResponseDTO.failure("no duncionando", "no duncionando")) ; //customerService.getCustomersWithManualPagination(row, page, searchValue, selectedFilter);
    }
}
