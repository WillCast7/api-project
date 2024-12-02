package com.aurealab.api.service.impl;

import com.aurealab.api.model.entity.PermissionEntity;
import com.aurealab.api.model.entity.PersonEntity;
import com.aurealab.api.model.entity.RolesEntity;
import com.aurealab.api.model.entity.UserEntity;
import com.aurealab.api.model.repository.PermissionRepository;
import com.aurealab.api.model.repository.RoleRepository;
import com.aurealab.api.model.repository.UserRepository;
import com.aurealab.api.service.TemplateService;
import com.aurealab.api.util.constants;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Slf4j
@Service
public class TemplateServiceImpl implements TemplateService {

    @Autowired
    PermissionRepository permissionRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserRepository userRepository;

    /**
     * Principal function for data template creation
     * @return string with a message
     */
    @Transactional
    public String createTemplate() {
        String response = constants.success.savedSuccess;
        log.info(constants.uitlLogs.separator);
        log.info("STARTING TEMPLATE DATA CREATION");
        log.info(constants.uitlLogs.separator);

        try {
            rolesCreation();
            userTemplateCreation();
        } catch (Exception error) {
            log.error("Error: ", error);
            response = constants.errors.saveError;
        }

        log.info(constants.uitlLogs.separator);
        log.info("OVERED TEMPLATE DATA CREATION");
        log.info(constants.uitlLogs.separator);

        return response;
    }

    public void userTemplateCreation() {
        log.info(constants.uitlLogs.separator);
        log.info("Creating Persons and Users");

        Set<RolesEntity> roles = new HashSet<>((Collection) roleRepository.findAll());

        createUser("willcast", "williamisrael210@gmail.com", "$2a$10$UWoUgwri.XXA.LGiHtGO3uinblEmzsCDkvW4eFZDSi.5fQW988S1a",//AureaLab1994**
                "William", "Castaño", "1143969222", "Cali - Valle del Cauca", LocalDate.of(1994, 10, 5),
                findRoleByName(roles, "SUPERUSER"));

        createUser("admin", "admin@gmail.com", "$2a$10$Nal2kboXo4QSqwbNt3jdS.Hkg7CDSLLqg6zRhABrMZdu.vv1K/bO.",//12345678
                "administrador", "AureaLab", "1234567890", "Cali - La Buitrera", LocalDate.of(2024, 12, 2),
                findRoleByName(roles, "ADMIN"));
    }

    /**
     * Creation of users
     * @param username username for login, is unique
     * @param email email for login, is unique
     * @param password password for login
     * @param names first and second names
     * @param surnames first and second lastnames
     * @param dni dni number
     * @param address address of the person
     * @param birthDate birthdate  in local date format
     * @param role role of type RoleEntity
     */
    private void createUser(String username, String email, String password,
                            String names, String surnames, String dni,
                            String address, LocalDate birthDate, RolesEntity role) {
        PersonEntity person = PersonEntity.builder()
                .dni(dni)
                .names(names)
                .surnames(surnames)
                .address(address)
                .birthDate(birthDate)
                .build();

        UserEntity user = UserEntity.builder()
                .email(email)
                .userName(username)
                .password(password)
                .person(person)
                .role(role)
                .accountNotLocked(true)
                .credentialNotExpired(true)
                .accountNotExpired(true)
                .isEnable(true)
                .build();

        userRepository.save(user);
        log.info("Created user: {}", username);
    }

    public RolesEntity findRoleByName(Set<RolesEntity> roles, String roleName) {
        return roles.stream()
                .filter(role -> role.getRole().equals(roleName))
                .findFirst()
                .orElse(null);
    }

    public void rolesCreation() {
        log.info(constants.uitlLogs.separator);
        log.info("Generating in memory Permissions");
        PermissionEntity read = PermissionEntity.builder()
                .name("READ")
                .build();
        PermissionEntity create = PermissionEntity.builder()
                .name("CREATE")
                .build();
        PermissionEntity update = PermissionEntity.builder()
                .name("UPDATE")
                .build();
        PermissionEntity superUser = PermissionEntity.builder()
                .name("SUPERUSER")
                .build();

        log.info(constants.uitlLogs.separator);
        log.info("Creating Roles");

        //All permissions
        createRoleIfNotExists("SUPERUSER", Set.of(read,create,update,superUser), "Super Usuario", constants.descriptions.superUser);
        createRoleIfNotExists("ADMIN", Set.of(read,create,update), "Administrador", constants.descriptions.admin);
        createRoleIfNotExists("SUPERVISOR", Set.of(read,create,update), "Supervisor", constants.descriptions.supervisor);
        createRoleIfNotExists("OPERATIVEUSER", Set.of(read,create), "Usuario operativo", constants.descriptions.operativeUser);
        createRoleIfNotExists("DIGITER", Set.of(read,create), "Digitador", constants.descriptions.digiter);

    }

    /**
     * validate if role exists; if it doesn't, it'll be created
     * @param name name of the roles
     * @param permissions the list permissions of the role
     * @param rolName is the rol name in spanish, used for the users
     * @param description is the description of role in spanish for the users
     */
    private void createRoleIfNotExists(String name, Set<PermissionEntity> permissions, String rolName, String description){
        if (Objects.equals(roleRepository.findByName(name), null)) {
            roleRepository.save(RolesEntity.builder()
                .role(name)
                .status(true)
                .rolDescription(description)
                .roleName(rolName)
                .perrmissionList(permissions)
                .build());
            log.info("Created role: {}", rolName);
        }
    }
}
