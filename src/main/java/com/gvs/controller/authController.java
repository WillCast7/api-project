package com.gvs.controller;

import com.gvs.dto.APIResponseDTO;
import com.gvs.dto.AuthResponse;
import com.gvs.dto.LoginRequest;
import com.gvs.service.impl.UserDetailServiceImpl;
import com.gvs.util.constants;
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
        return this.userDetailService.loginUser(userRequest);
    }

}
