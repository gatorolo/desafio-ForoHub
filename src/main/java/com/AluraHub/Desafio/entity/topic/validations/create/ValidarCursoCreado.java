package com.AluraHub.Desafio.entity.topic.validations.create;

import com.AluraHub.Desafio.entity.course.repository.CourseRepository;
import com.AluraHub.Desafio.entity.topic.dto.CrearTopicoDTO;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidarCursoCreado implements ValidarTopicoCreado {

    @Autowired
    private CourseRepository repo;

    @Override
    public void validate(CrearTopicoDTO data) {
        var existeCurso = repo.existsById(data.cursoId());
        if(!existeCurso) {
            throw new ValidationException("Éste curso no existe");
        }
        var cursoHabilitado = repo.findById(data.cursoId()).get().getActive();
        if (!cursoHabilitado) {
            throw new ValidationException("Éste curso no esá disponible");
        }
    }
}
