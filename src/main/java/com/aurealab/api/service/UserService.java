package com.aurealab.api.service;

import com.aurealab.api.dto.APIResponseDTO;
import com.aurealab.api.dto.UserDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {
    public ResponseEntity<APIResponseDTO<List<UserDTO>>> getUsers(int itemsPerPage, int activePage);
    public ResponseEntity<APIResponseDTO<UserDTO>> getUser(Long id);
    public ResponseEntity<APIResponseDTO<String>> saveUser(UserDTO user);
}
