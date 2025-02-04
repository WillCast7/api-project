package com.gvs.model.crm.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Data
@Table(name = "software_validation") // Correcto
public class SoftwareValidationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String code;

    private String description;

    @Column(nullable = false)
    private boolean status;

    @OneToMany(mappedBy = "softwareValidation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SoftwareValidationDataEntity> data = new ArrayList<>();

}