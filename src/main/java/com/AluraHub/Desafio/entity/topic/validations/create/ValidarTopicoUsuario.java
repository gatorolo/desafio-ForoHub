package com.AluraHub.Desafio.entity.topic.validations.create;

import com.AluraHub.Desafio.entity.topic.dto.CrearTopicoDTO;
import com.AluraHub.Desafio.security.user.UserRepo;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidarTopicoUsuario implements ValidarTopicoCreado {

    @Autowired
    private UserRepo userRepo;

    @Override
    public void validate(CrearTopicoDTO data) {
     var existeUsuario = userRepo.existsById(Math.toIntExact(data.usuarioId()));
     if (!existeUsuario) {
         throw new ValidationException("Ése usuario no existe");
     }

     var usuarioHabilitado = userRepo.findById(data.usuarioId()).get().getEnabled();
     if (!usuarioHabilitado) {
         throw new ValidationException("Éste usuario fue inhabilitado");
     }
    }
}
