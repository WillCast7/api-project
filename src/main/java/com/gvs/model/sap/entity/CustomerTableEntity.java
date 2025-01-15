package com.gvs.model.sap.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Data
public class CustomerTableEntity {

    @Column(name = "CardName")
    private String name;

    @Id
    @Column(name = "CardCode")
    private String nit;

    private Integer actual;

    private Integer anterior1;

    private Integer anterior2;

    private Integer anterior3;

    @Column(name = "City")
    private String city;

    @Column(name = "SlpName")
    private String asesor;
}
