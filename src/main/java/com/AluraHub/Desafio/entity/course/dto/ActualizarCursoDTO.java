package com.AluraHub.Desafio.entity.course.dto;

import com.AluraHub.Desafio.entity.course.Category;

public record ActualizarCursoDTO(
        String name,
        Category category,
        Boolean active
) {
}
