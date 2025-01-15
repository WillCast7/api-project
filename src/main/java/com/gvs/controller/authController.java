package com.gvs.controller;

import com.gvs.dto.APIResponseDTO;
import com.gvs.dto.AuthResponse;
import com.gvs.dto.LoginRequest;
import com.gvs.dto.MenuDTO;
import com.gvs.service.impl.UserDetailServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/login")
public class authController {

    @Autowired
    UserDetailServiceImpl userDetailService;

    @PostMapping
    ResponseEntity<APIResponseDTO<AuthResponse>> login(@RequestBody @Valid LoginRequest userRequest){
        return this.userDetailService.loginUser(userRequest);
    }

}
