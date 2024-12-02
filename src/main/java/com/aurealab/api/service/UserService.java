package com.aurealab.api.service;

import com.aurealab.api.dto.APIResponseDTO;
import com.aurealab.api.dto.UserDTO;

import java.util.List;

public interface UserService {
    public APIResponseDTO<List<UserDTO>> getUsers(int itemsPerPage, int activePage);
    public void searchName();
}
