package com.gvs.service.impl;

import com.gvs.dto.APIResponseDTO;
import com.gvs.model.crm.entity.SoftwareValidationDataEntity;
import com.gvs.model.crm.entity.SoftwareValidationEntity;
import com.gvs.service.SoftwareValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class SoftwareValidationServiceImpl implements SoftwareValidationService {

    @Autowired
    SoftwareValidationEntity softwareValidationEntity;

    public ResponseEntity<APIResponseDTO<Set<SoftwareValidationEntity>>> getSoftwareValidation(){

        return ResponseEntity.ok(APIResponseDTO.failure("error", "error"));
    }

}
