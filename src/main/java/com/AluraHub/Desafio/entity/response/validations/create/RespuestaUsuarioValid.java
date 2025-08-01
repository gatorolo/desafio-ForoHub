package com.AluraHub.Desafio.entity.response.validations.create;

import com.AluraHub.Desafio.entity.response.dto.CrearRespuestaDTO;
import com.AluraHub.Desafio.security.user.UserRepo;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RespuestaUsuarioValid implements ValidarRespuestaCreada {

    @Autowired
    private UserRepo repo;

    public void validate(CrearRespuestaDTO data) {
        var usuarioExiste = repo.existsById(Math.toIntExact(data.usuarioId()));

        if(!usuarioExiste) {
            throw new ValidationException("éste usuario no existe");
        }

        var usuarioHabilitado = repo.findById(Math.toIntExact(data.usuarioId())).get().isEnabled();

        if(!usuarioHabilitado) {
            throw new ValidationException(("Éste usuario no existe"));
        }
    }
}
