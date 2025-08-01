package com.AluraHub.Desafio.entity.response.dto;

import com.AluraHub.Desafio.entity.response.Response;

import java.time.LocalDateTime;

public record DetalleRespuestaDTO(
        Long id,
        String mensaje,
        LocalDateTime fechaCreacion,
        LocalDateTime ultimaActualizacion,
        Boolean solucion,
        Boolean borrado,
        Long usuarioId,
        String username,
        Long topicoId,
        String topico
) {

    public DetalleRespuestaDTO(Response respuesta) {
        this(
                respuesta.getId(),
                respuesta.getMensaje(),
                respuesta.getFechaCreacion(),
                respuesta.getUltimaActualizacion(),
                respuesta.isSolucion(),
                respuesta.isBorrado(),
                respuesta.getUsuario().getId(),
                respuesta.getUsuario().getUsername(),
                respuesta.getTopico().getId(),
                respuesta.getTopico().getTitulo()
        );
    }
}
