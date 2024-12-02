package com.aurealab.api.controller;

import com.aurealab.api.dto.APIResponseDTO;
import com.aurealab.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@PreAuthorize("permitAll()")
public class authController {

    @Autowired
    UserService userService;

    @GetMapping("/login")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    APIResponseDTO<String> login(){
                return APIResponseDTO.success("", "Todo melo", "200");
    }

    @GetMapping("/login2")
    APIResponseDTO<String> login2(){
        userService.searchName();
        return APIResponseDTO.success("", "Todo melo", "200");
    }
}
