package com.AluraHub.Desafio.entity.course.dto;

import com.AluraHub.Desafio.entity.course.Category;
import com.AluraHub.Desafio.entity.course.Course;

public record DetalleCursoDTO(
        Long id,
        String name,
        Category category,
        Boolean active
) {
    public DetalleCursoDTO(Course course){
        this(
                course.getId(),
                course.getName(),
                course.getCategory(),
                course.getActive()
        );
    }
}
