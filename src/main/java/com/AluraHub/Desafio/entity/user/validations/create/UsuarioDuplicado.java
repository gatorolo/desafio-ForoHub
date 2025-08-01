package com.AluraHub.Desafio.entity.user.validations.create;

import com.AluraHub.Desafio.entity.user.dto.CrearUserDTO;
import com.AluraHub.Desafio.entity.user.repository.UserRepo;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UsuarioDuplicado implements ValidarUsuario {

    @Autowired
    private UserRepo repo;

    @Override
    public void validate(CrearUserDTO data) {

        var usuarioDduplicado = repo.findByUsername(data.username());
        if (usuarioDduplicado != null) {
            throw new ValidationException("Usuario Existente");
        }
    }
}
