package com.gvs.service.impl;

import com.gvs.dto.*;
import com.gvs.model.b1.entity.UserEntity;
import com.gvs.model.b1.repository.UserRepository;
import com.gvs.model.crm.entity.MenuItemEntity;
import com.gvs.model.crm.entity.RoleEntity;
import com.gvs.model.crm.repository.RoleRepository;
import com.gvs.util.DecryptPass;
import com.gvs.util.JwtUtils;
import com.gvs.util.constants;
import com.gvs.util.exceptions.BaseException;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.*;

@Slf4j
@Service
public class UserDetailServiceImpl {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    MenuServiceImpl menuServiceImpl;

    @Autowired
    private JwtUtils jwtUtils;

    /**
     * Carga los detalles del usuario incluyendo roles, permisos y menú.
     */
    public UserDetails loadUserDetails(String roleName, RoleEntity role, String username) throws UsernameNotFoundException {
        log.info("Cargando detalles del usuario con rol: {}", roleName);

        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();
        role.getPerrmissionList()
                .forEach(permission -> authorityList.add(new SimpleGrantedAuthority(permission.getName())));

        authorityList.add(new SimpleGrantedAuthority("ROLE_".concat(role.getRole())));

        log.info("Detalles del usuario cargados correctamente");
        return new User(username, "password", authorityList);
    }

    /**
     * Proceso de inicio de sesión del usuario.
     */
    @Transactional("crmTrxManager")
    public ResponseEntity<APIResponseDTO<AuthResponse>> loginUser(LoginRequest userLogin) {

        // Validar credenciales
        UserEntity userEntity = validateCredentials(userLogin.username(), userLogin.password());
        System.out.println("userEntity");
        System.out.println(userEntity);
        if (userEntity == null) {
            APIResponseDTO<AuthResponse> response = APIResponseDTO.failure(
                    constants.errors.invalidUserOrPass, constants.descriptions.loginError
            );
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
        try {
            Optional<RoleEntity> roleEntity = roleRepository.findByValidator(userEntity.getRole());
            if (roleEntity.isEmpty()) {
                log.warn("No se encontró ningún rol asociado al validador: {}", userEntity.getRole());
                throw new UsernameNotFoundException(constants.errors.invalidRole);
            }

            RoleEntity role = roleEntity.get();

            // Cargar detalles del usuario
            UserDetails userDetails = loadUserDetails(userEntity.getRole(), role, userLogin.username());
            log.info("id " + userEntity.getId().toString());
            Set<MenuItemEntity> optionalMenu = menuServiceImpl.getMenuByRole(userEntity.getRole());
            if (optionalMenu.isEmpty()){
                log.warn("No se encontró ningún menu asociado al validador: {}", userEntity.getRole());
                throw new UsernameNotFoundException(constants.errors.invalidMenu);
            }
            Set<MenuDTO> menuList = new HashSet();

            optionalMenu.stream().forEach(menuItemEntity -> menuList.add(setMenuEntityToDTO(menuItemEntity)));
            String accessToken = jwtUtils.createToken(
                    new UsernamePasswordAuthenticationToken(userLogin.username(), null, userDetails.getAuthorities())
            );


            AuthResponse authResponse = new AuthResponse(userLogin.username(), accessToken, menuList);


            return ResponseEntity.ok(APIResponseDTO.success(authResponse, constants.success.loginSuccess)); // Enviar cookie en la respuesta

        } catch (Exception e) {
            log.error("error: {}" + e);
            throw new ConcurrentModificationException(e);
        }
    }

    /**
     * Valida las credenciales del usuario en la base de datos.
     */
    @Transactional("b1TrxManager")
    public UserEntity validateCredentials(String username, String password) {
        try {
            return userRepository.login(username, DecryptPass.decrypt(password))
                    .orElseThrow(() -> new BaseException(
                            constants.errors.invalidUserOrPass,
                            constants.descriptions.loginError,
                            HttpStatus.UNAUTHORIZED) {});
        } catch (BaseException e) {
            log.warn("Credenciales inválidas para el usuario: {}", username);
            throw e;
        } catch (Exception e) {
            log.error("Error al validar las credenciales del usuario", e);
            throw new BaseException(constants.errors.loginError, constants.descriptions.loginError, e) {};
        }
    }

    public MenuDTO setMenuEntityToDTO(MenuItemEntity menuParam) {
        if (menuParam == null) {
            return null;
        }

        return MenuDTO.builder()
                .id(menuParam.getId())
                .name(menuParam.getName())
                .route(menuParam.getRoute())
                .icon(menuParam.getIcon())
                .father(menuParam.getFather())
                .orderMenu(menuParam.getOrderMenu())
                .build();
    }
}
