package com.AluraHub.Desafio.entity.response.validations.update;

import com.AluraHub.Desafio.entity.response.dto.ActualizarRespuestaDTO;

public interface ValidarRespuestaActualizada {
    void validate(ActualizarRespuestaDTO data, Long respuestaId);
}
