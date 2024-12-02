package com.aurealab.api.service.impl;

import com.aurealab.api.model.entity.UserEntity;
import com.aurealab.api.model.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

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
}
