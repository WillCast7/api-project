package com.aurealab.api.controller;

import com.aurealab.api.dto.APIResponseDTO;
import com.aurealab.api.dto.UserDTO;
import com.aurealab.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")

public class userController {

    @Autowired
    private UserService userService;


    @GetMapping(produces = "application/json")
    @PreAuthorize("hasAuthority('READ')")
    public APIResponseDTO<List<UserDTO>> getUsers(@RequestParam(defaultValue = "10") int itemsPerPage,
                                                  @RequestParam(defaultValue = "0") int activePage) {
        return userService.getUsers(itemsPerPage, activePage);
    }

    @PostMapping(produces = "application/json")
    public APIResponseDTO<List<UserDTO>> postUsers(@RequestParam(defaultValue = "10") int itemsPerPage,
                                                  @RequestParam(defaultValue = "0") int activePage) {
        return userService.getUsers(itemsPerPage, activePage);
    }

    @PutMapping(produces = "application/json")
    public APIResponseDTO<List<UserDTO>> putUsers(@RequestParam(defaultValue = "10") int itemsPerPage,
                                                   @RequestParam(defaultValue = "0") int activePage) {
        return userService.getUsers(itemsPerPage, activePage);
    }

    @PatchMapping(produces = "application/json")
    public APIResponseDTO<List<UserDTO>> patchUsers(@RequestParam(defaultValue = "10") int itemsPerPage,
                                                  @RequestParam(defaultValue = "0") int activePage) {
        return userService.getUsers(itemsPerPage, activePage);
    }
}
