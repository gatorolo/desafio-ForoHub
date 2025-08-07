package com.AluraHub.Desafio.entity.topic.validations.update;

import com.AluraHub.Desafio.entity.topic.dto.ActualizarTopicoDTO;
import org.springframework.stereotype.Component;

@Component
public interface ValidarTopicoActualizado {
    void validate(ActualizarTopicoDTO data);
}
