package com.aurealab.api.controller;

import com.aurealab.api.dto.APIResponseDTO;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@PreAuthorize("denyAll()")
public class authController {

    @GetMapping("/login")
    @PreAuthorize("permitAll()")
    APIResponseDTO<String> login(){
        return APIResponseDTO.success("", "Todo melo", "200");
    }
}
