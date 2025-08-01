package com.AluraHub.Desafio.entity.user.dto;

import com.AluraHub.Desafio.entity.user.Role;

public record ActualizarUserDTO(
        String firstname,
        String lastname,
        String country,
        Boolean enabled
) {
}
