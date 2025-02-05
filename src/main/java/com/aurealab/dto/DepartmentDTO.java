package com.aurealab.dto;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class DepartmentDTO {
    private String name;
    private String code;
}
