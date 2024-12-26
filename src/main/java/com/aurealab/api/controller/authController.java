package com.aurealab.api.controller;

import com.aurealab.api.dto.APIResponseDTO;
import com.aurealab.api.dto.AuthResponse;
import com.aurealab.api.dto.LoginRequest;
import com.aurealab.api.service.impl.UserDetailServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class authController {

    @Autowired
    UserDetailServiceImpl userDetailService;

    @PostMapping
    APIResponseDTO<AuthResponse> login(@RequestBody @Valid LoginRequest userRequest){
        return APIResponseDTO.success(this.userDetailService.loginUser(userRequest), "Todo melo", "200");
    }

}
