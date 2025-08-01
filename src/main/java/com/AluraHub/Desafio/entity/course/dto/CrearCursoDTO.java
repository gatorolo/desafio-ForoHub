package com.AluraHub.Desafio.entity.course.dto;

import com.AluraHub.Desafio.entity.course.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CrearCursoDTO(
        @NotBlank
        String name,
        @NotNull
        Category category
) {
}
