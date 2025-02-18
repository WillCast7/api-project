package com.aurealab.controller;

import com.aurealab.dto.APIResponseDTO;
import com.aurealab.model.aurea.entity.SoftwareValidationEntity;
import com.aurealab.service.SoftwareValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.swing.*;
import java.util.Set;

@RestController
@RequestMapping("/dog")
public class softwareValidationController {

    @Autowired
    SoftwareValidationService softwareValidationService;

    @GetMapping("/validaciondesoftware")
    public ResponseEntity<APIResponseDTO<Set<SoftwareValidationEntity>>> getRules(){

        return softwareValidationService.getSoftwareValidation();

    }
}
