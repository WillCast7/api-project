package com.aurealab.api.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Data
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;

    @Column(name = "username", unique = true)
    private String userName;

    @NotNull
    private String password;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private PersonEntity person;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    private RolesEntity role;

    @Column(name = "is_enable")
    private boolean isEnable = true;

    @Column(name = "account_not_expired")
    private boolean accountNotExpired;

    @Column(name = "account_not_locked")
    private boolean accountNotLocked;

    @Column(name = "credential_not_expired")
    private boolean credentialNotExpired;

}