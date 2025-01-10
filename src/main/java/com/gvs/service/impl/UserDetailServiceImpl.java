package com.gvs.service.impl;

import com.gvs.dto.APIResponseDTO;
import com.gvs.dto.AuthResponse;
import com.gvs.dto.LoginRequest;
import com.gvs.dto.UserDTO;
import com.gvs.model.b1.entity.UserEntity;
import com.gvs.model.b1.repository.UserRepository;
import com.gvs.model.crm.entity.RoleEntity;
import com.gvs.model.crm.repository.RoleRepository;
import com.gvs.util.JwtUtils;
import com.gvs.util.constants;
import com.gvs.util.exceptions.BaseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
public class UserDetailServiceImpl {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    private JwtUtils jwtUtils;


    /**
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    //@Override
    @Transactional("crmTrxManager")
    public UserDetails findByValidator(String role, String username) throws UsernameNotFoundException {
    log.info("starting the user detail service of the role : {}", role);

        Optional<RoleEntity> roleEntity = roleRepository.findByValidator(role);

        log.info("finish the user detail service");
        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();

        if(roleEntity.isPresent()) {
            authorityList.add(new SimpleGrantedAuthority("ROLE_".concat(roleEntity.get().getRole())));
            roleEntity.get().getPerrmissionList().forEach(permission -> authorityList.add(new SimpleGrantedAuthority(permission.getName())));
            System.out.println("authorityList");
            System.out.println(authorityList);

        }else {
            log.info("no se encontro ningun usuario con ese rol");
        }
        return new User(username,
                "password",
                /**userEntity.isEnable(),
                 userEntity.isAccountNotExpired(),
                 userEntity.isCredentialNotExpired(),
                 userEntity.isAccountNotLocked(),**/
                authorityList
        );

    }


    public APIResponseDTO<AuthResponse> loginUser(LoginRequest userLogin){
        APIResponseDTO<AuthResponse> response;
        String username = userLogin.username();
        String password = userLogin.password();

        UserEntity userEntity = validateCredentials(username, password);
        if (Objects.equals(userEntity, null)) {
            response =  APIResponseDTO.failure(constants.errors.invalidUserOrPass, "401", "error");
        } else{

            Authentication authentication = this.authenticate(userEntity, password);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            String accessToken = jwtUtils.createToken(authentication);

            AuthResponse authResponse = new AuthResponse(username, "user Loged succesfuly", accessToken, true);

            response = APIResponseDTO.success(authResponse, constants.success.loginSuccess, "200");
        }

        return  response;
    }

    @Transactional("b1TrxManager")
    public UserEntity validateCredentials(String username, String password){
        UserEntity userEntity = null;

        try {
            Optional<UserEntity> credentialWithoutValidation = userRepository.login(username, password);
            log.info("resultado {}" + credentialWithoutValidation);
            if (!credentialWithoutValidation.isEmpty()){
                userEntity = credentialWithoutValidation.get();
            }else{
                log.info(constants.errors.invalidUserOrPass);
            }

        } catch (Exception e) {
            log.error("Ocurrio una exception: {}" + e);
            throw new RuntimeException(e);
        }
        return  userEntity;
    }

    public Authentication authenticate(UserEntity userEntity, String password){

        UserDetails userDetails = findByValidator(userEntity.getRole(), userEntity.getUserName());

        return new UsernamePasswordAuthenticationToken(userEntity.getUserName(), password, userDetails.getAuthorities());
    }

}
