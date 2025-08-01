package com.AluraHub.Desafio.entity.topic.dto;

import com.AluraHub.Desafio.entity.topic.Estado;

public record ActualizarTopicoDTO(

        String titulo,
        String mensaje,
        Estado estado,
        Long cursoId
) {


}
