package com.aurealab.model.sap.entity;

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
public class AdvisorListEntity {
    @Id
    @Column(name = "SlpCode")
    private Long id;

    @Column(name = "SlpName")
    private String name;
}