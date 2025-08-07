package com.AluraHub.Desafio.entity.topic.validations.create;

import com.AluraHub.Desafio.entity.topic.dto.CrearTopicoDTO;
import com.AluraHub.Desafio.entity.topic.repository.TopicRepo;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TopicoDuplicado implements ValidarTopicoCreado {

    @Autowired
    private TopicRepo topicRepo;

    public void validate(CrearTopicoDTO data) {
        var topicoDuplicado = topicRepo.existsByTituloAndMensaje(data.titulo(), data.mensaje());
        if (topicoDuplicado) {
            throw new ValidationException("Éste tópico ya existe. Revisa /Tópicos/" + topicRepo.findByTitulo(data.titulo()).getId());
        }
    }


}
