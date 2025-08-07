package com.AluraHub.Desafio.entity.user.dto;

public record ActualizarUserDTO(
        String firstname,
        String lastname,
        String country,
        Boolean enabled
) {
}
