package com.aurealab.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(@NotBlank String username,
                           @NotBlank String password,
                           @NotBlank String nit) {
}
