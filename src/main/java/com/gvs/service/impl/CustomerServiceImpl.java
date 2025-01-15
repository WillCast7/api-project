package com.gvs.service.impl;

import com.gvs.dto.APIResponseDTO;
import com.gvs.dto.PageableResponseDTO;
import com.gvs.model.sap.entity.CustomerTableEntity;
import com.gvs.model.sap.repository.CustomerRepository;
import com.gvs.service.CustomerService;
import com.gvs.util.constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Transactional("sapTrxManager")
    public ResponseEntity<APIResponseDTO<Set<CustomerTableEntity>>> getCustomersWithManualPagination(int page, int rows) {
        try {
            // Paginación manual desde la base de datos
            Set<CustomerTableEntity> customerList = customerRepository.findList(page, rows);

            if (customerList.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(
                        APIResponseDTO.failure(constants.messages.noData, constants.descriptions.empty)
                );
            }

            
            long totalElements = customerRepository.countAllRecords(); // Obtener el total de registros
            PageableResponseDTO pageableResponse = new PageableResponseDTO(
                    page, rows, totalElements
            );

            return ResponseEntity.ok(
                    APIResponseDTO.withPageable(
                            customerList,
                            constants.success.findedSuccess,
                            pageableResponse
                    )
            );
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(
                    APIResponseDTO.failure(constants.messages.error, e.getMessage())
            );
        }
    }
}
