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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
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
            if (users.hasContent()) {
                List<UserDTO> dtoList = new ArrayList<>();
                UserMapper userMapper = new UserMapper();

                for (UserEntity user : users) {
                    dtoList.add(userMapper.setEntityToDTO(user));
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
    }

}
