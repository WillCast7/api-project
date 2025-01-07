package com.aurealab.api.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Data
@Table(name = "persons")
public class PersonEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long personId;

    private String dni;

    private String names;

    private String surnames;

    @Column(name = "phone", unique = true)
    private String phoneNumber;

    private String address;

    @Column(name = "birth")
    private LocalDate birthDate;
}
