package com.AluraHub.Desafio.entity.topic.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CrearTopicoDTO(

        @NotBlank
        String titulo,
        @NotBlank
        String mensaje,
        @NotNull
        Long usuarioId,
        @NotNull
        Long cursoId
) {
}
