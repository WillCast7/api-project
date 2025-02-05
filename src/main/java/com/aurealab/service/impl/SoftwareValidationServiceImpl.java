package com.aurealab.service.impl;

import com.aurealab.dto.APIResponseDTO;
import com.aurealab.model.aurea.entity.SoftwareValidationEntity;
import com.aurealab.service.SoftwareValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
public class SoftwareValidationServiceImpl implements SoftwareValidationService {

   /* @Autowired
    SoftwareValidationEntity softwareValidationEntity;*/

    @Transactional("aureaTrxManager")
    public ResponseEntity<APIResponseDTO<Set<SoftwareValidationEntity>>> getSoftwareValidation(){

        return ResponseEntity.ok(APIResponseDTO.failure("error", "error"));
    }

}
