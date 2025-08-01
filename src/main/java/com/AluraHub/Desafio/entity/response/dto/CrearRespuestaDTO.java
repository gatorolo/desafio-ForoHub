package com.AluraHub.Desafio.entity.response.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CrearRespuestaDTO(

        @NotBlank
        String mensaje,
        @NotNull
        Long usuarioId,
        @NotNull
        long topicoId
) {

}
