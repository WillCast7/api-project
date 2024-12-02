package com.aurealab.api.model.entity;

import jakarta.persistence.*;
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
@Table(name = "roles")
public class RolesEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rolId;

    private String role;

    @Column(name = "description")
    private String rolDescription;

    @Column(name = "role_name")
    private String roleName;

    private boolean status;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "role_permission", joinColumns = @JoinColumn(name = "role_id"), inverseJoinColumns = @JoinColumn(name = "permission_id"))
    private Set<PermissionEntity> perrmissionList = new HashSet<>();

    // Relación inversa: Un rol puede ser asignado a muchos usuarios
    @OneToMany(mappedBy = "role", fetch = FetchType.LAZY) // Cambiar a OneToMany
    private Set<UserEntity> users = new HashSet<>();
}
