package com.aurealab.api.controller;

import com.aurealab.api.dto.APIResponseDTO;
import com.aurealab.api.dto.AuthResponse;
import com.aurealab.api.dto.LoginRequest;
import com.aurealab.api.service.impl.UserDetailServiceImpl;
import com.aurealab.api.util.constants;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class authController {

    @Autowired
    UserDetailServiceImpl userDetailService;

    @PostMapping
    ResponseEntity<APIResponseDTO<AuthResponse>> login(@RequestBody @Valid LoginRequest userRequest){
        return userDetailService.loginUser(userRequest);
    }

}
