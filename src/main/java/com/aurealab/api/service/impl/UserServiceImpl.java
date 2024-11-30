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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    public APIResponseDTO<List<UserDTO>> getUsers(int itemPerPage, int activePage) {

        APIResponseDTO<List<UserDTO>> response;
        Page<UserEntity> users;

        final Pageable pageable = PageRequest.of(activePage, itemPerPage);
        try {
            users = userRepository.findAll(pageable);
            if (users.hasContent()) { // Verifica si hay contenido
                List<UserDTO> dtoList = new ArrayList<>();
                UserMapper userMapper = new UserMapper(); // Instancia del mapeador

                for (UserEntity user : users) {
                    dtoList.add(userMapper.setEntityToDTO(user)); // Usa el método correcto
                }
                response = APIResponseDTO.withPageable(dtoList, constants.messages.consultGood, "200", users.getPageable());
            } else {
                response = APIResponseDTO.failure(constants.messages.noData, "400", "vacio");
            }
        } catch (Exception e) {
            response = APIResponseDTO.failure(constants.errors.findError + " los usuarios", "500", e.getMessage());
        }
        return response;
    }

    public class UserMapper {

        // Convierte UserEntity a UserDTO
        public UserDTO setEntityToDTO(UserEntity userParam) {
            if (userParam == null) {
                return null; // Manejo de nulos
            }

            PersonDTO dtPerson = setPersonToDTO(userParam.getPerson());
            RoleDTO dtRole = setRoleToDTO(userParam.getRole());

            return UserDTO.builder()
                    .userId(userParam.getUserId())
                    .userName(userParam.getUserName())
                    .email(userParam.getEmail())
                    .person(dtPerson)
                    .role(dtRole)
                    .build();
        }

        // Convierte RolesEntity a RoleDTO
        public RoleDTO setRoleToDTO(RolesEntity role) {
            if (role == null) {
                return null; // Manejo de nulos
            }

            return RoleDTO.builder()
                    .rolId(role.getRolId())
                    .rolDescription(role.getRolDescription())
                    .role(role.getRole())
                    .status(role.isStatus())
                    .build();
        }

        // Convierte PersonEntity a PersonDTO
        public PersonDTO setPersonToDTO(PersonEntity person) {
            if (person == null) {
                return null; // Manejo de nulos
            }

            return PersonDTO.builder()
                    .personId(person.getPersonId())
                    .dni(person.getDni())
                    .names(person.getNames())
                    .surNames(person.getSurnames()) // Asegúrate que este campo sea correcto
                    .address(person.getAddress())
                    .phoneNumber(person.getPhoneNumber())
                    .birthDate(person.getBirthDate())
                    .build();
        }
    }




}
