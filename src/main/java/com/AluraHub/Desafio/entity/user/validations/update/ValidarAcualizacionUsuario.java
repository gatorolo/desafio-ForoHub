package com.AluraHub.Desafio.entity.user.validations.update;

import com.AluraHub.Desafio.entity.user.dto.ActualizarUserDTO;
import com.AluraHub.Desafio.entity.user.repository.UserRepo;
import com.AluraHub.Desafio.entity.user.validations.create.ValidarUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidarAcualizacionUsuario implements ValidarActualizacionUsuario {

    @Autowired
    private UserRepo repo;

    public void validate(ActualizarUserDTO data) {

    }
}
