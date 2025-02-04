package com.gvs.model.crm.entity;

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
@Table(name = "softwarevalidation_multimedia") // Correcto
public class SoftwareValidationMultimediaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "multimedia_order")
    private String multimediaOrder;

    private String description;

    private String multimedia;

    @ManyToOne
    @JoinColumn(name = "softwarevalidationdata_id", referencedColumnName = "id")
    private SoftwareValidationDataEntity softwareValidationData;
}