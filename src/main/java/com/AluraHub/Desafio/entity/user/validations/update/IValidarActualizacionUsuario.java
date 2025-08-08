package com.AluraHub.Desafio.entity.user.validations.update;

import com.AluraHub.Desafio.entity.user.dto.ActualizarUserDTO;
import org.springframework.stereotype.Component;

@Component
public interface IValidarActualizacionUsuario  {

        void validate(ActualizarUserDTO data);


}
