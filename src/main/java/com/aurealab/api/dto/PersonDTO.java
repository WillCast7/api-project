package com.aurealab.api.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class PersonDTO {

    private Long personId;

    @NotBlank(message = "El numero de identificacion no puede estar vacío.")
    private String dni;

    @NotBlank(message = "falta llenar los nombres no puede estar vacío.")
    private String names;

    @NotBlank(message = "falta llenar los nombres no puede estar vacío.")
    private String surNames;

    @Size(max = 10, message = "El numero telefonico debe tener 10 numeros")
    private String phoneNumber;

    private String address;

    private Date birthDate;

}
