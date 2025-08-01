package com.AluraHub.Desafio.controller;

import com.AluraHub.Desafio.entity.course.Course;
import com.AluraHub.Desafio.entity.course.dto.ActualizarCursoDTO;
import com.AluraHub.Desafio.entity.course.dto.CrearCursoDTO;
import com.AluraHub.Desafio.entity.course.dto.DetalleCursoDTO;
import com.AluraHub.Desafio.entity.course.repository.CourseRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/cursos")
public class CourseController {

    @Autowired
    private CourseRepository courseRepository;

    @PostMapping
    @Transactional
    //@Operation(summary = "Registrar un nuevo curso")
    public ResponseEntity<DetalleCursoDTO> crearTopico(@RequestBody @Valid CrearCursoDTO crearCursoDTO,
                                                       UriComponentsBuilder uriBuilder) {
        Course course = new Course(crearCursoDTO);
        courseRepository.save(course);
        var uri = uriBuilder.path("/cursos/{i}").buildAndExpand(course.getId()).toUri();
        return ResponseEntity.created(uri).body(new DetalleCursoDTO(course));
    }

    @GetMapping("/all")
    public ResponseEntity<Page<DetalleCursoDTO>> listarCursos(Pageable pageable) {
        var paginas = courseRepository.findAll(pageable).map(DetalleCursoDTO.class);
        return ResponseEntity.ok(paginas);
    }

    @GetMapping
    public ResponseEntity<Page<Course>> listarCursosActivos(Pageable pageable) {
        var pagina = courseRepository.findAllByActivoTrue(pageable);
        return ResponseEntity.ok(pagina);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetalleCursoDTO> listarUnCurso(@PathVariable Long id) {
        Course curso = (Course) courseRepository.getReferenceById(id);
        var datosDelCurso = new DetalleCursoDTO(
                curso.getId(),
                curso.getName(),
                curso.getCategory(),
                curso.getActive()
        );
        return ResponseEntity.ok(datosDelCurso);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<DetalleCursoDTO> actualizarCurso(@RequestBody ActualizarCursoDTO actualizarCursoDTO ,@PathVariable long id) {
        Course course = (Course) courseRepository.getReferenceById(id);

        course.actualizarCurso(actualizarCursoDTO);

        var datosCurso = new DetalleCursoDTO(
                course.getId(),
                course.getName(),
                course.getCategory(),
                course.getActive()
        );
        return ResponseEntity.ok(datosCurso);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> eliminarCurso(@PathVariable long id) {
        Course curso = (Course) courseRepository.getReferenceById(id);
        curso.eliminarCurso();
        return ResponseEntity.noContent().build();
    }
}