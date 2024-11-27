package com.aurealab.api.model.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
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

    @Column(name = "phone")
    private String phoneNumber;

    private String address;

    @Column(name = "birth")
    private Date birthDate;
}
