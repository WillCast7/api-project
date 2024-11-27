package com.aurealab.api.controller;

import com.aurealab.api.dto.APIResponseDTO;
import com.aurealab.api.dto.UserDTO;
import com.aurealab.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class userController {

    @Autowired
    private UserService userService;

    @GetMapping(produces = "application/json")
    public APIResponseDTO<List<UserDTO>> getUsers(@RequestParam(defaultValue = "10") int itemsPerPage,
                                                  @RequestParam(defaultValue = "0") int activePage) {
        return userService.getUsers(itemsPerPage, activePage);
    }
}
