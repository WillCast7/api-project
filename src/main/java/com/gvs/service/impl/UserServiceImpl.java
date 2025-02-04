package com.gvs.service.impl;

import com.gvs.dto.APIResponseDTO;
import com.gvs.dto.RoleDTO;
import com.gvs.dto.UserDTO;
import com.gvs.model.crm.entity.RoleEntity;
import com.gvs.model.b1.entity.UserEntity;
import com.gvs.model.b1.repository.UserRepository;
import com.gvs.service.UserService;
import com.gvs.util.constants;
import com.gvs.util.exceptions.BaseException;
import com.gvs.util.exceptions.DataPersistenceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Value("${security.users.defaultpass}")
    private String defaultPass;

    public APIResponseDTO<List<UserDTO>> getUsers(int itemPerPage, int activePage) {

        APIResponseDTO<List<UserDTO>> response;
        Page<UserEntity> users;

        final Pageable pageable = PageRequest.of(activePage, itemPerPage);
        try {
            users = userRepository.findAll(pageable);
            if (users.hasContent()) {
                List<UserDTO> dtoList = new ArrayList<>();
                UserMapper userMapper = new UserMapper();

                for (UserEntity user : users) {
                    dtoList.add(userMapper.setEntityToDTO(user));
                }
                response = APIResponseDTO.withPageable(dtoList, constants.messages.consultGood, users.getPageable());
            } else {
                response = APIResponseDTO.failure(constants.messages.noData, "vacio");
            }
        } catch (Exception e) {
            response = APIResponseDTO.failure(constants.errors.findError + " los usuarios", e.getMessage());
        }
        return response;
    }

    public class UserMapper {
        //Convert entity to dto
        public UserDTO setEntityToDTO(UserEntity userParam) {
            if (userParam == null) {
                return null;
            }


            return UserDTO.builder()
                    .userId(userParam.getId())
                    .userName(userParam.getUserName())
                    .email(userParam.getEmail())
                    .build();
        }

        // RolesEntity to RoleDTO conversion
        public RoleDTO setRoleToDTO(RoleEntity role) {
            if (role == null) {
                return null;
            }

            return RoleDTO.builder()
                    .rolId(role.getRolId())
                    .rolDescription(role.getRolDescription())
                    .role(role.getRole())
                    .status(role.isStatus())
                    .roleName(role.getRoleName())
                    .build();
        }


        //Convert DTO to Entity
        public UserEntity setDTOToEntity(UserDTO userParam) {
            if (userParam == null) {
                return null;
            }

            RoleEntity dtRole = setRoleToEntity(userParam.getRole());

            UserEntity uaerEntity = new UserEntity();
            uaerEntity.setId(userParam.getUserId());
            uaerEntity.setUserName(userParam.getUserName());

            UserEntity userEntity = new UserEntity();
            userEntity.setId(userParam.getUserId());
            userEntity.setUserName(userParam.getUserName());

            userEntity.setEmail(userParam.getEmail());

            return userEntity;
        }

        // RoleDTO to RoleEntity conversion
        public RoleEntity setRoleToEntity(RoleDTO role) {
            if (role == null) {
                return null;
            }

            RoleEntity roleEntity = new RoleEntity();
            roleEntity.setRolId(role.getRolId());
            roleEntity.setRole(role.getRole());
            roleEntity.setRoleName(role.getRoleName());
            roleEntity.setStatus(role.isStatus());
            return roleEntity;
        }

    }

    public APIResponseDTO<UserDTO> getUser(Long id) {
        APIResponseDTO<UserDTO> response;
        Optional<UserEntity> userOptional = userRepository.findById(id);

        UserDTO userDTO = null;
        if (userOptional.isPresent()) {
            UserMapper userMapper = new UserMapper();
            userDTO = userMapper.setEntityToDTO(userOptional.get());
            response = APIResponseDTO.success(userDTO, constants.messages.consultGood);
        } else {
            response = APIResponseDTO.failure(constants.messages.dontFoundByID, constants.messages.dontFoundByID);
        }

        return response;
    }

    public APIResponseDTO<String> saveUser(UserDTO user) {
        APIResponseDTO<String> response;
        UserEntity userEntity;

        try {
            UserMapper userMapper = new UserMapper();
            userEntity = userMapper.setDTOToEntity(user);
            userRepository.save(userEntity);

            log.info("Usuario guardado: {}", userEntity.getUserName());

            response = APIResponseDTO.success(constants.success.savedSuccess, constants.success.savedSuccess);

        } catch (DataIntegrityViolationException e) {
            String exceptionMessage = "Ya existe un usuario con ese ";

            if (e.getMessage().contains("users_email_key")) {
                throw new DataPersistenceException("Correo electrónico ya registrado.");
            } else if (e.getMessage().contains("users_username_key")) {
                throw new DataPersistenceException("Nombre de usuario ya registrado.");
            } else if (e.getMessage().contains("persons_phone_key")) {
                throw new DataPersistenceException("Número telefónico ya registrado.");
            } else {
                throw new DataPersistenceException("Datos duplicados.");
            }

        } catch (Exception e) {
            log.error("Error inesperado: {}", e.getMessage());
            throw new BaseException(constants.errors.saveError, constants.errors.internalServerError, e) {
            };
        }

        return response;
    }

}
