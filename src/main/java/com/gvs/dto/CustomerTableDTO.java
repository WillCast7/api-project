package com.gvs.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class CustomerTableDTO {
    private String name;
    private String nit;
    private Integer actual;
    private Integer anterior1;
    private Integer anterior2;
    private Integer anterior3;
    private String city;
    private String asesor;
}
