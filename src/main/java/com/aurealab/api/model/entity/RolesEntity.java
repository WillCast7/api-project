package com.aurealab.api.model.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table(name = "roles")
public class RolesEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rolId;

    private String rol;

    @Column(name = "description")
    private String rolDescription;

    private boolean status;

    @OneToMany(mappedBy = "role")
    private Set<UserEntity> users;
}
