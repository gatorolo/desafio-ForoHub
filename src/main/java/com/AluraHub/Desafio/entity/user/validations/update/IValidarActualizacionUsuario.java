package com.AluraHub.Desafio.entity.user.validations.update;

import com.AluraHub.Desafio.entity.user.dto.ActualizarUserDTO;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public interface IValidarActualizacionUsuario  {

        void validate(ActualizarUserDTO data);


}
