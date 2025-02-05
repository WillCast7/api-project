package com.aurealab.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class UserDTO {

    private Long userId;

    @NotBlank(message = "El correo no puede estar vacío.")
    @Email(message = "El correo electrónico debe ser válido.")
    private String email;

    @Size(min = 6, message = "El nombre de usuario debe tener minimo 6 caracteres")
    private String userName;

    @Size(min = 8, message = "La contraseña debe tener minimo 8 caracteres")
    private String password;

    private PersonDTO person;

    private RoleDTO role;

}
