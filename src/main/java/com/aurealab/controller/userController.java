package com.aurealab.controller;

import com.aurealab.dto.APIResponseDTO;
import com.aurealab.dto.UserDTO;
import com.aurealab.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
<<<<<<< HEAD:src/main/java/com/gvs/controller/userController.java
=======
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.User;
>>>>>>> e44fb2bd7d772988ee34dc7afb4df901cc70d725:src/main/java/com/aurealab/api/controller/userController.java
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
