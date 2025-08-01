package com.AluraHub.Desafio.entity.course;

import com.AluraHub.Desafio.entity.course.dto.ActualizarCursoDTO;
import com.AluraHub.Desafio.entity.course.dto.CrearCursoDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cursos")
@Entity(name = "curso")
@EqualsAndHashCode(of = "id")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Enumerated(EnumType.STRING)
    private Category category;
    private Boolean active;

    public Course(CrearCursoDTO crearCursoDTO) {
        this.name = crearCursoDTO.name();
        this.category = crearCursoDTO.category();
        this.active = true;
    }
        public void actualizarCurso(ActualizarCursoDTO actualizarCursoDTO) {
            if(actualizarCursoDTO.name() != null) {
                this.name = actualizarCursoDTO.name();
            }
            if(actualizarCursoDTO.category() != null) {
                this.category = actualizarCursoDTO.category();
            }
            if(actualizarCursoDTO.active() != null) {
                this.active = actualizarCursoDTO.active();
            }
    }
}

