package com.aurealab.api.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;
import java.util.Optional;

/**
 * Clase que representa una respuesta genérica para la API.
 *
 * @param <T> Tipo de datos que se incluye en la respuesta.
 */
@Data
@Builder
public class APIResponseDTO<T> implements Serializable {

	private final boolean state;
	private final String message;
	private final String error;
	private final String code;
	private final Optional<T> data; // Usar Optional para evitar nulos
	private final Pageable pageable;

	// Constructor privado para forzar el uso del builder
	private APIResponseDTO(boolean state, String message, String error, String code, Optional<T> data, Pageable pageable) {
		this.state = state;
		this.message = message;
		this.error = error;
		this.code = code;
		this.data = data;
		this.pageable = pageable;
	}

	// Métodos estáticos para crear respuestas comunes
	public static <T> APIResponseDTO<T> success(T data, String message, String code) {
		return APIResponseDTO.<T>builder()
				.state(true)
				.message(message)
				.code(code)
				.data(Optional.ofNullable(data))
				.build();
	}

	public static <T> APIResponseDTO<T> failure(String message, String code, String error) {
		return APIResponseDTO.<T>builder()
				.state(false)
				.message(message)
				.error(error)
				.code(code)
				.data(Optional.empty())
				.build();
	}

	public static <T> APIResponseDTO<T> withPageable(T data, String message, String code, Pageable pageable) {
		return APIResponseDTO.<T>builder()
				.state(true)
				.message(message)
				.code(code)
				.data(Optional.ofNullable(data))
				.pageable(pageable)
				.build();
	}
}