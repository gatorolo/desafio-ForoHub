package com.AluraHub.Desafio.entity.user;

import com.AluraHub.Desafio.entity.user.dto.ActualizarUserDTO;
import com.AluraHub.Desafio.entity.user.dto.CrearUserDTO;
import com.AluraHub.Desafio.security.user.Role;
import jakarta.persistence.*;
import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Usuario")
@EqualsAndHashCode(of = "id")
public class User {

    private static final Long  serialVersionId = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;
    private  String username;
    private String password;
    private String firstname;
    private String lastname;
    private String country;
    private Boolean enabled;

    public User(CrearUserDTO crearUserDTO, String hashedPassword) {
        this.username = crearUserDTO.username();
        this.password = hashedPassword;
        this.firstname = capitalizado(crearUserDTO.firstname());
        this.lastname = capitalizado(crearUserDTO.lastname());
        this.country = crearUserDTO.country();
        this.enabled = true;
    }

    public void actualizarUsuario(ActualizarUserDTO actualizarUserDTO) {

        if (actualizarUserDTO.firstname() != null) {
            this.firstname = capitalizado(actualizarUserDTO.firstname());
        }
        if (actualizarUserDTO.lastname() != null) {
            this.lastname = capitalizado(actualizarUserDTO.lastname());
        }
        if (actualizarUserDTO.country() != null) {
            this.country = actualizarUserDTO.country();
        }
        if (actualizarUserDTO.enabled() != null) {
            this.enabled = actualizarUserDTO.enabled();
        }
    }

    public void eliminarUsuario(){ this.enabled = false;}

    private String capitalizado(String string) {
        return string.substring(0,1).toUpperCase()+string.substring(1).toLowerCase();
    }
}
