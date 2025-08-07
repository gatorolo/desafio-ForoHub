package com.AluraHub.Desafio.controller;

import org.springframework.security.core.Authentication;
import com.AluraHub.Desafio.security.user.User;
import com.AluraHub.Desafio.entity.course.Course;
import com.AluraHub.Desafio.entity.course.dto.ActualizarCursoDTO;
import com.AluraHub.Desafio.entity.course.dto.CrearCursoDTO;
import com.AluraHub.Desafio.entity.course.dto.DetalleCursoDTO;
import com.AluraHub.Desafio.entity.course.repository.CourseRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("api/v1/cursos")
public class CourseController {

    @Autowired
    private CourseRepository courseRepo;


    @PostMapping
    @Transactional
    public ResponseEntity<DetalleCursoDTO> crearCurso(@RequestBody @Valid CrearCursoDTO crearCursoDTO,
                                                      UriComponentsBuilder uriBuilder,
                                                      Authentication authentication) {
        User usuario = (User) authentication.getPrincipal();
        Course course = new Course(crearCursoDTO);
        course.setCreador(usuario);

        courseRepo.save(course);

        var uri = uriBuilder.path("/api/v1/cursos/{id}").buildAndExpand(course.getId()).toUri();
        return ResponseEntity.created(uri).body(new DetalleCursoDTO(course));
    }


    @GetMapping("/all")
    public ResponseEntity<Page<DetalleCursoDTO>> listarCursos(Pageable pageable) {
        var paginas = courseRepo.findAll(pageable).map(DetalleCursoDTO::new);
        return ResponseEntity.ok(paginas);
    }

    @GetMapping
    public ResponseEntity<Page<Course>> listarCursosActivos(Pageable pageable) {
        var pagina = courseRepo.findAllByActiveTrue(pageable);
        return ResponseEntity.ok(pagina);
    }


    @GetMapping("/{id}")
    public ResponseEntity<DetalleCursoDTO> listarUnCurso(@PathVariable Long id) {
        Course curso = courseRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Curso no encontrado"));
        return ResponseEntity.ok(new DetalleCursoDTO(curso));
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<DetalleCursoDTO> actualizarCurso(@RequestBody ActualizarCursoDTO actualizarCursoDTO ,@PathVariable long id) {
        Course course = (Course) courseRepo.getReferenceById(id);

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
        Course curso = (Course) courseRepo.getReferenceById(id);
        curso.eliminarCurso();
        return ResponseEntity.noContent().build();
    }

}