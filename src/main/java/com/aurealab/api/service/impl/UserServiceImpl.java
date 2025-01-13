package com.aurealab.api.service.impl;

import com.aurealab.api.dto.APIResponseDTO;
import com.aurealab.api.dto.PersonDTO;
import com.aurealab.api.dto.RoleDTO;
import com.aurealab.api.dto.UserDTO;
import com.aurealab.api.model.entity.PersonEntity;
import com.aurealab.api.model.entity.RolesEntity;
import com.aurealab.api.model.entity.UserEntity;
import com.aurealab.api.model.repository.UserRepository;
import com.aurealab.api.service.UserService;
import com.aurealab.api.util.constants;
import com.aurealab.api.util.exceptions.BaseException;
import com.aurealab.api.util.exceptions.DataPersistenceException;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Value("${security.users.defaultpass}")
    private String defaultPass;

    public ResponseEntity<APIResponseDTO<List<UserDTO>>> getUsers(int itemPerPage, int activePage) {

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
                return ResponseEntity.ok(APIResponseDTO.withPageable(dtoList, constants.messages.consultGood, users.getPageable()));
            } else {
                return ResponseEntity.ofNullable(APIResponseDTO.failure(constants.messages.noData, "vacio"));
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(APIResponseDTO.failure(constants.errors.findError + " los usuarios", e.getMessage()));
        }

    }

    public class UserMapper {
        //Convert entity to dto
        public UserDTO setEntityToDTO(UserEntity userParam) {
            if (userParam == null) {
                return null;
            }

            PersonDTO dtPerson = setPersonToDTO(userParam.getPerson());
            RoleDTO dtRole = setRoleToDTO(userParam.getRole());

            return UserDTO.builder()
                    .userId(userParam.getId())
                    .userName(userParam.getUserName())
                    .email(userParam.getEmail())
                    .person(dtPerson)
                    .role(dtRole)
                    .build();
        }

        // RolesEntity to RoleDTO conversion
        public RoleDTO setRoleToDTO(RolesEntity role) {
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

        // PersonEntity to PersonDTO conversion
        public PersonDTO setPersonToDTO(PersonEntity person) {
            if (person == null) {
                return null;
            }

            return PersonDTO.builder()
                    .personId(person.getPersonId())
                    .dni(person.getDni())
                    .names(person.getNames())
                    .surNames(person.getSurnames())
                    .address(person.getAddress())
                    .phoneNumber(person.getPhoneNumber())
                    .birthDate(person.getBirthDate())
                    .build();
        }

        //Convert DTO to Entity
        public UserEntity setDTOToEntity(UserDTO userParam) {
            if (userParam == null) {
                return null;
            }

            PersonEntity dtPerson = setPersonToEntity(userParam.getPerson());
            RolesEntity dtRole = setRoleToEntity(userParam.getRole());

            UserEntity uaerEntity = new UserEntity();
            uaerEntity.setId(userParam.getUserId());
            uaerEntity.setUserName(userParam.getUserName());

            UserEntity userEntity = new UserEntity();
            userEntity.setId(userParam.getUserId());
            userEntity.setUserName(userParam.getUserName());
            userEntity.setRole(dtRole);
            userEntity.setPassword(defaultPass);
            userEntity.setEmail(userParam.getEmail());
            userEntity.setPerson(dtPerson);

            return userEntity;
        }

        // RoleDTO to RoleEntity conversion
        public RolesEntity setRoleToEntity(RoleDTO role) {
            if (role == null) {
                return null;
            }

            RolesEntity roleEntity = new RolesEntity();
            roleEntity.setRolId(role.getRolId());
            roleEntity.setRole(role.getRole());
            roleEntity.setRoleName(role.getRoleName());
            roleEntity.setStatus(role.isStatus());
            return roleEntity;
        }

        // PersonEntity to PersonDTO conversion
        public PersonEntity setPersonToEntity(PersonDTO person) {
            if (person == null) {
                return null;
            }

            PersonEntity personEntity = new PersonEntity();
            personEntity.setPersonId(person.getPersonId());
            personEntity.setDni(person.getDni());
            personEntity.setNames(person.getNames());
            personEntity.setSurnames(person.getSurNames());
            personEntity.setAddress(person.getAddress());
            personEntity.setPhoneNumber(person.getPhoneNumber());
            personEntity.setBirthDate(person.getBirthDate());

            return personEntity;
        }
    }

    public ResponseEntity<APIResponseDTO<UserDTO>> getUser(Long id) {
        Optional<UserEntity> userOptional = userRepository.findById(id);

        UserDTO userDTO = null;
        if (userOptional.isPresent()) {
            UserMapper userMapper = new UserMapper();
            userDTO = userMapper.setEntityToDTO(userOptional.get());
            return ResponseEntity.ok().body(APIResponseDTO.success(userDTO, constants.messages.consultGood));
        } else {
            return ResponseEntity.ofNullable(APIResponseDTO.failure(constants.messages.dontFoundByID, constants.messages.dontFoundByID));
        }
    }

    public ResponseEntity<APIResponseDTO<String>> saveUser(UserDTO user) {
        UserEntity userEntity;

        try {
            UserMapper userMapper = new UserMapper();
            userEntity = userMapper.setDTOToEntity(user);
            userRepository.save(userEntity);

            log.info("Usuario guardado: {}", userEntity.getUserName());

            return ResponseEntity.ok().body(APIResponseDTO.success(constants.success.savedSuccess, constants.success.savedSuccess));

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
    }

    public ResponseEntity<APIResponseDTO<String>> updateUser(UserDTO user, Long id){
        APIResponseDTO response;
        try{
            if(true){
                return ResponseEntity.status(HttpStatus.OK).body(APIResponseDTO.success(null, "modificado exitosamente"));
            }
            return ResponseEntity.status(HttpStatus.OK).body(APIResponseDTO.success(null, "modificado exitosamente"));

        }catch (Exception e) {
            log.error("error : {}" + e);
            throw new BaseException(constants.errors.saveError, constants.errors.internalServerError, e) {
            };
        }
    }
}
