package com.AluraHub.Desafio.entity.response;

import com.AluraHub.Desafio.entity.response.dto.CrearRespuestaDTO;
import com.AluraHub.Desafio.entity.response.dto.ActualizarRespuestaDTO;
import com.AluraHub.Desafio.entity.topic.Topic;
import com.AluraHub.Desafio.security.user.User;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "respuestas")
@Entity(name = "respuesta")
@EqualsAndHashCode(of = "id")
public class Response {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String mensaje;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;

    @Column(name = "ultima_actualizacion")
    private LocalDateTime ultimaActualizacion;

    private boolean solucion;
    private boolean borrado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private User usuario;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "topico_id")
    private Topic topico;


    public Response(CrearRespuestaDTO crearRespuestaDTO, LocalDateTime fechaCreacion, LocalDateTime ultimaActualizacion, boolean solucion, boolean borrado, User usuario, Topic topico) {

        this.mensaje = crearRespuestaDTO.mensaje();
        this.fechaCreacion = LocalDateTime.now();
        this.ultimaActualizacion = LocalDateTime.now();
        this.solucion = false;
        this.borrado = false;
        this.usuario = usuario;
        this.topico = topico;
    }

    public Response(CrearRespuestaDTO dto, User usuario, Topic topico) {
        this.mensaje = dto.mensaje();
        this.usuario = usuario;
        this.topico = topico;
        this.fechaCreacion = LocalDateTime.now();
        this.ultimaActualizacion = LocalDateTime.now();
        this.solucion = false;
        this.borrado = false;
    }

    public void actualizarRespuesta(ActualizarRespuestaDTO actualizarRespuestaDTO) {
        if (actualizarRespuestaDTO.mensaje() != null) {
            this.mensaje = actualizarRespuestaDTO.mensaje();
        }
        this.ultimaActualizacion = LocalDateTime.now();
    }

    public void eliminarRespuesta() {
        this.borrado = true;
    }

}
