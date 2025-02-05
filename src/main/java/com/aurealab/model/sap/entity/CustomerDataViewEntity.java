package com.aurealab.model.sap.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Data
public class CustomerDataViewEntity {

    @Column(name = "Phone1")
    private String phone1;

    @Column(name = "Phone2")
    private String phone2;

    @Column(name = "E_Mail")
    private String email;

    @Id
    @Column(name = "CardCode")
    private String nit;

    @Column(name = "Address")
    private String address;

    @Column(name = "City")
    private String city;
}
