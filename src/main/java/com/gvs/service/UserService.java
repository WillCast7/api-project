package com.gvs.service;

<<<<<<< HEAD:src/main/java/com/gvs/service/UserService.java
import com.gvs.dto.APIResponseDTO;
import com.gvs.dto.UserDTO;
=======
import com.aurealab.api.dto.APIResponseDTO;
import com.aurealab.api.dto.UserDTO;
import org.springframework.http.ResponseEntity;
>>>>>>> e44fb2bd7d772988ee34dc7afb4df901cc70d725:src/main/java/com/aurealab/api/service/UserService.java

import java.util.List;

public interface UserService {
    public ResponseEntity<APIResponseDTO<List<UserDTO>>> getUsers(int itemsPerPage, int activePage);
    public ResponseEntity<APIResponseDTO<UserDTO>> getUser(Long id);
    public ResponseEntity<APIResponseDTO<String>> saveUser(UserDTO user);
}
