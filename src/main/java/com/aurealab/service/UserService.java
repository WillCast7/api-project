package com.aurealab.service;

import com.aurealab.dto.APIResponseDTO;
import com.aurealab.dto.UserDTO;

import java.util.List;

public interface UserService {
    public ResponseEntity<APIResponseDTO<List<UserDTO>>> getUsers(int itemsPerPage, int activePage);
    public ResponseEntity<APIResponseDTO<UserDTO>> getUser(Long id);
    public ResponseEntity<APIResponseDTO<String>> saveUser(UserDTO user);
}
