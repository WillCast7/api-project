package com.gvs.model.b1.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Data
@Table(name = "St_Usuarios")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // Cambiado a Integer

    @Column(name = "UserName", unique = true)
    private String userName;

    @Column(name = "Nombre")
    private String names;

    @Column(name = "Email", unique = true)
    private String email;

    @Column(name = "Extension")
    private String phoneExtension;

    @Column(name = "Password")
    private byte[] password; // Cambiado a byte[] para varbinary

    @Column(name = "Activo")
    private int isEnable; // Cambiado a int

    @Column(name = "Mapa")
    private Integer map; // Cambiado a Integer

    @Column(name = "SedeId")
    private Integer idHeadquarters; // Cambiado a Integer

    @Column(name = "Departamento_Id")
    private Integer idDepartment; // Cambiado a Integer

    // Si necesitas manejar fechas:
    @Column(name = "CreatedAt")
    private java.util.Date createdAt;

    @Column(name = "UpdatedAt")
    private java.util.Date updatedAt;

    @Column(name = "CambiarClave")
    private Boolean changePassword; // Cambiado a Boolean para bit

    @Column(name = "Pin")
    private Integer pin; // Cambiado a Integer

    @Column(name = "role")
    private String role; // Cambiado a Integer

}
