package com.aurealab.model.aurea.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Data
@Table(name = "menus") // Correcto
public class MenuItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    private String father;

    private String route;

    @Column(name = "menu_order")
    private int orderMenu;

    private String icon;
<<<<<<< HEAD:src/main/java/com/gvs/model/crm/entity/MenuItemEntity.java
=======

    // Relación con MenuRoleEntity
    @OneToMany(mappedBy = "menu", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<MenuRoleEntity> menuRoles;
>>>>>>> e44fb2bd7d772988ee34dc7afb4df901cc70d725:src/main/java/com/aurealab/api/model/entity/MenuItemEntity.java
}
