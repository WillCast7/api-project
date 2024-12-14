package com.aurealab.api.service.impl;

import com.aurealab.api.dto.AuthResponse;
import com.aurealab.api.dto.LoginRequest;
import com.aurealab.api.model.entity.UserEntity;
import com.aurealab.api.model.repository.UserRepository;
import com.aurealab.api.util.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    log.info("starting the user detail service of: {}", username);

        UserEntity userEntity = userRepository.findUserEntityByUserName(username)
                .orElseThrow(() -> new UsernameNotFoundException("El usuario " + username + " no existe."));

        log.info("finish the user detail service");

        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();
        authorityList.add(new SimpleGrantedAuthority("ROLE_".concat(userEntity.getRole().getRole())));
        userEntity.getRole().getPerrmissionList().forEach(permission->authorityList.add(new SimpleGrantedAuthority(permission.getName())));
        System.out.println("authorityList");
        System.out.println(authorityList);

        return new User(userEntity.getUserName(),
            userEntity.getPassword(),
            userEntity.isEnable(),
            userEntity.isAccountNotExpired(),
            userEntity.isCredentialNotExpired(),
            userEntity.isAccountNotLocked(),
            authorityList
        );

    }


    public AuthResponse loginUser(LoginRequest userLogin){
        String username = userLogin.username();
        String password = userLogin.password();

        Authentication authentication = this.authenticate(username, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String accessToken = jwtUtils.createToken(authentication);

        AuthResponse authResponse = new AuthResponse(username, "user Loged succesfuly", accessToken, true);

        return  authResponse;
    }

    public Authentication authenticate(String username, String password){
        UserDetails userDetails = this.loadUserByUsername(username);
        if (userDetails == null){
            throw new BadCredentialsException("invalid username or password");
        }

        if(!passwordEncoder.matches(password, userDetails.getPassword())){
            throw new BadCredentialsException("invalid username or password");
        }

        return new UsernamePasswordAuthenticationToken(username, userDetails.getPassword(), userDetails.getAuthorities());
    }

}
