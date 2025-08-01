package com.AluraHub.Desafio.entity.topic;

import com.AluraHub.Desafio.entity.course.Course;
import com.AluraHub.Desafio.entity.topic.dto.ActualizarTopicoDTO;
import com.AluraHub.Desafio.entity.topic.dto.CrearTopicoDTO;
import com.AluraHub.Desafio.security.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@SuppressWarnings("ALL")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "topicos")
@Entity(name = "topico")
@EqualsAndHashCode(of = "id")
public class Topic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String mensaje;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;

    @Column(name = "ultima_actualizacion")
    private LocalDateTime ultimaActualizacion;

    @Enumerated(EnumType.STRING)
    private Estado estado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private User usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curso_id")
    private Course curso;

    public Topic(CrearTopicoDTO crearTopicoDTO, User usuario, Course curso ) {
        this.titulo = crearTopicoDTO.titulo();
        this.mensaje = crearTopicoDTO.mensaje();
        this.fechaCreacion = LocalDateTime.now();
        this.ultimaActualizacion = LocalDateTime.now();
        this.estado = Estado.OPEN;
        this.usuario = usuario;
        this.curso = curso;
    }

    public void actualizarTopicoConCurso(ActualizarTopicoDTO actualizarTopicoDTO) {
        if (actualizarTopicoDTO.titulo() != null) {
            this.titulo = actualizarTopicoDTO.titulo();
        }
        if (actualizarTopicoDTO.mensaje() != null) {
            this.mensaje = actualizarTopicoDTO.mensaje();
        }
        if (actualizarTopicoDTO.estado() != null) {
            this.estado = actualizarTopicoDTO.estado();
        }
        if (actualizarTopicoDTO.cursoId() != null) {
            this.curso = curso;
        }
        this.ultimaActualizacion = LocalDateTime.now();
    }

    public void actualizarTopico(ActualizarTopicoDTO actualizarTopicoDTO) {
        if (actualizarTopicoDTO.titulo() != null) {
            this.titulo = actualizarTopicoDTO.titulo();
        }
        if (actualizarTopicoDTO.mensaje() != null) {
            this.mensaje = actualizarTopicoDTO.mensaje();
        }
        if (actualizarTopicoDTO.estado() != null) {
            this.estado = actualizarTopicoDTO.estado();
        }
        this.ultimaActualizacion = LocalDateTime.now();
    }

    public void eliminarTopico(){
        this.estado = Estado.DELETED;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

}
