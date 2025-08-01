package com.AluraHub.Desafio.entity.response.validations.update;

import com.AluraHub.Desafio.entity.response.Response;
import com.AluraHub.Desafio.entity.response.dto.ActualizarRespuestaDTO;
import com.AluraHub.Desafio.entity.response.repository.ResponseRepo;
import com.AluraHub.Desafio.entity.topic.Estado;
import com.AluraHub.Desafio.entity.topic.repository.TopicRepo;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SolucionDuplicada implements ValidarRespuestaActualizada{

    @Autowired
    private ResponseRepo repo;

    @Autowired
    private TopicRepo topicRepo;

    public void validate(ActualizarRespuestaDTO data, Long respuestaId) {
        if (data.solucion()) {
            Response respuesta = repo.getReferenceById(respuestaId);
            var topicoResuelto = topicRepo.getReferenceById(respuesta.getTopico().getId());
            if(topicoResuelto.getEstado() == Estado.CLOSED) {
                throw new ValidationException("Este tópico ya está cerrado");
            }
        }
    }
}
