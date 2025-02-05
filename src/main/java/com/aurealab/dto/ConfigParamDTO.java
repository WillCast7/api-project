package com.aurealab.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class ConfigParamDTO {
    private Long id;
    private String name;
    private String shortname;
}
