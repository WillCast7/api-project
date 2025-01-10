package com.gvs.service;

import com.gvs.dto.APIResponseDTO;
import com.gvs.dto.UserDTO;

import java.util.List;

public interface UserService {
    public APIResponseDTO<List<UserDTO>> getUsers(int itemsPerPage, int activePage);
    public APIResponseDTO<UserDTO> getUser(Long id);
    public APIResponseDTO<String> saveUser(UserDTO user);
}
