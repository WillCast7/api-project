package com.aurealab.api.controller;

import com.aurealab.api.dto.APIResponseDTO;
import com.aurealab.api.dto.UserDTO;
import com.aurealab.api.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/administracion/usuarios")

public class userController {

    @Autowired
    private UserService userService;


    @GetMapping(produces = "application/json")
    public ResponseEntity<APIResponseDTO<List<UserDTO>>> getUsers(@RequestParam(defaultValue = "10") int itemsPerPage,
                                                                  @RequestParam(defaultValue = "0") int activePage) {
        return userService.getUsers(itemsPerPage, activePage);
    }

    @GetMapping(value = "/{id}" ,produces = "application/json")
    public ResponseEntity<APIResponseDTO<UserDTO>> getUser(@PathVariable Long id) {
        return userService.getUser(id);
    }

    @PostMapping(produces = "application/json")
    public ResponseEntity<APIResponseDTO<String>> postUsers(@Valid @RequestBody UserDTO user) {
        return userService.saveUser(user);
    }

    @PutMapping(produces = "application/json")
    public ResponseEntity<APIResponseDTO<String>> putUsers(@RequestBody UserDTO user) {
        return userService.saveUser(user);
    }

    @PatchMapping(produces = "application/json")
    public ResponseEntity<APIResponseDTO<String>> patchUsers(@Valid @RequestBody UserDTO user) {
        return userService.saveUser(user);
    }
}
