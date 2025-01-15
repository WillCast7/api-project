package com.gvs.service;

import com.gvs.dto.APIResponseDTO;
import com.gvs.model.sap.entity.CustomerTableEntity;
import org.springframework.http.ResponseEntity;

import java.util.Set;

public interface CustomerService {
    public ResponseEntity<APIResponseDTO<Set<CustomerTableEntity>>> getCustomersWithManualPagination(int page, int rows);

}
