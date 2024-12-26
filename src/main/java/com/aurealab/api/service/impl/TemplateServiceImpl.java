package com.aurealab.api.service.impl;

import com.aurealab.api.model.entity.*;
import com.aurealab.api.model.repository.MenuRepository;
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
import java.util.*;

@Slf4j
@Service
public class TemplateServiceImpl implements TemplateService {

    @Autowired
    PermissionRepository permissionRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    MenuRepository menuRepository;

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
            menusCreation();
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
        createRoleIfNotExists("SUPERUSER", Set.of(read,create,update,superUser), "Super Usuario", constants.roleDescriptions.superUser);
        createRoleIfNotExists("ADMIN", Set.of(read,create,update), "Administrador", constants.roleDescriptions.admin);
        createRoleIfNotExists("SUPERVISOR", Set.of(read,create,update), "Supervisor", constants.roleDescriptions.supervisor);
        createRoleIfNotExists("OPERATIVEUSER", Set.of(read,create), "Usuario operativo", constants.roleDescriptions.operativeUser);
        createRoleIfNotExists("DIGITER", Set.of(read,create), "Digitador", constants.roleDescriptions.digiter);

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

    private void menusCreation(){
        log.info(constants.uitlLogs.separator);
        log.info("Creating Menus");
        Iterable<RolesEntity> rolesIterable = roleRepository.findAll();

        //Convert the rolesIterable of iterable type to a rolesList of a set type
        Set<RolesEntity> rolesList = new HashSet<>((Collection<? extends RolesEntity>) rolesIterable);

        log.info("Be will created the menu");
        //creation of the menus
        createMenu("Asesores", "Administracion", "/administracion/asesores", 1, ".", rolesList);
        createMenu("Usuarios", "Administracion", "/administracion/usuarios", 1, ".", rolesList);
        createMenu("Mi cuenta", "Configuracion", "/configuracion/micuenta", 1, ".", rolesList);
        createMenu("Mi CDA", "Configuracion", "/configuracion/micda", 1, ".", rolesList);

    }

    private void createMenu(String name, String father, String route, int orderMenu, String icon, Set<RolesEntity> roles){
         menuRepository.save(MenuItemEntity.builder()
                .name(name)
                .father(father)
                .route(route)
                .orderMenu(orderMenu)
                .icon(icon)
                .roles(roles)
                .build()
         );
        log.info("Created the item Menu: {}", name + " of " + father);
    }
}
