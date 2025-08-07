package com.AluraHub.Desafio.entity.response.validations.create;

import com.AluraHub.Desafio.entity.response.dto.CrearRespuestaDTO;
import com.AluraHub.Desafio.entity.topic.Estado;
import com.AluraHub.Desafio.entity.topic.Topic;
import com.AluraHub.Desafio.entity.topic.repository.TopicRepo;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class RespuestaTopicValid implements ValidarRespuestaCreada {

    @Autowired
    private TopicRepo topicRepo;

    @Override
    public void validate(CrearRespuestaDTO data) {

        Optional<Topic> optionalTopic = topicRepo.findById(data.topicoId());
        if (optionalTopic.isEmpty()) {
            throw new ValidationException("Éste tópico no existe.");
        }

        Topic topico = optionalTopic.get();
        if (topico.getEstado() != Estado.OPEN) {

            throw new ValidationException("El tópico no está abierto y no puede recibir respuestas.");
        }
    }
}