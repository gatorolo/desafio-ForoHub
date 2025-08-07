package com.AluraHub.Desafio.entity.topic.validations.update;

import com.AluraHub.Desafio.entity.course.repository.CourseRepository;
import com.AluraHub.Desafio.entity.topic.dto.ActualizarTopicoDTO;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidarCursoActualizado implements ValidarTopicoActualizado{

    @Autowired
    private CourseRepository repository;

    @Override
    public void validate(ActualizarTopicoDTO data) {
        if (data.cursoId() != null) {

            var optionalCurso = repository.findById(data.cursoId());


            if (optionalCurso.isEmpty()) {
                throw new ValidationException("Éste Curso No Existe");
            }

            var curso = optionalCurso.get();
            if (!curso.getActive()) {
                throw new ValidationException("Éste Curso No Está Habilitado");
            }
        }
    }
}
