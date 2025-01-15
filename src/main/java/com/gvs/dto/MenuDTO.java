package com.gvs.dto;

import lombok.*;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Clase que representa una respuesta genérica para la API.
 *
 * @param <T> Tipo de datos que se incluye en la respuesta.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class MenuDTO {
	private Long id;
	private String name;
	private String father;
	private String route;
	private int orderMenu;
	private String icon;
}