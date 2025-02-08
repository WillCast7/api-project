package com.aurealab.model.aurea.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Data
@Table(name = "subscriptions")
public class SubscriptionsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private EnterpriseEntity enterprise;

    private String plan_name;

    private Float price;

    private Date start_date;
    private Date end_date;
    private Boolean is_active;
    private Date created_at;
    private Date updated_at;
}
