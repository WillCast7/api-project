package com.aurealab.model.aurea.entity;

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
@Table(name = "kaizen_roles") // Correcto
public class RoleEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rolId;

    @Column(name = "role")
    private String role;

    @Column(name = "validator")
    private String validator;

    @Column(name = "description")
    private String rolDescription;

    @Column(name = "role_name")
    private String roleName;

    private boolean status;

<<<<<<< HEAD:src/main/java/com/gvs/model/crm/entity/RoleEntity.java
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "kaizen_rolepermission", // Correcto
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id")
    )
    private Set<PermissionEntity> perrmissionList = new HashSet<>();
}
=======
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "role_permission", joinColumns = @JoinColumn(name = "role_id"), inverseJoinColumns = @JoinColumn(name = "permission_id"))
    private Set<PermissionEntity> perrmissionList = new HashSet<>();

    @OneToMany(mappedBy = "role", fetch = FetchType.LAZY)
    private Set<UserEntity> users = new HashSet<>();

}
>>>>>>> e44fb2bd7d772988ee34dc7afb4df901cc70d725:src/main/java/com/aurealab/api/model/entity/RolesEntity.java
