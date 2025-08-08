package com.AluraHub.Desafio.controller;

import com.AluraHub.Desafio.entity.course.Course;
import com.AluraHub.Desafio.entity.course.repository.CourseRepository;
import com.AluraHub.Desafio.entity.response.Response;
import com.AluraHub.Desafio.entity.response.dto.DetalleRespuestaDTO;
import com.AluraHub.Desafio.entity.response.repository.ResponseRepo;
import com.AluraHub.Desafio.entity.topic.Estado;
import com.AluraHub.Desafio.entity.topic.Topic;
import com.AluraHub.Desafio.entity.topic.dto.ActualizarTopicoDTO;
import com.AluraHub.Desafio.entity.topic.dto.CrearTopicoDTO;
import com.AluraHub.Desafio.entity.topic.dto.DetalleTopicoDTO;
import com.AluraHub.Desafio.entity.topic.repository.TopicRepo;
import com.AluraHub.Desafio.entity.topic.validations.create.ValidarTopicoCreado;
import com.AluraHub.Desafio.entity.topic.validations.update.ValidarTopicoActualizado;
import com.AluraHub.Desafio.entity.user.repository.UserRepo;
import com.AluraHub.Desafio.entity.user.User;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.List;

@SuppressWarnings("ALL")
@RestController
@RequestMapping("/api/v1/topicos")
public class TopicoController {

    @Autowired
    private TopicRepo topicRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private CourseRepository cursoRepo;

    @Autowired
    private ResponseRepo respuestaRepo;

    @Autowired
    private List<ValidarTopicoCreado> crearValidadores;

    @Autowired
    private List<ValidarTopicoActualizado> actualizarValidadores;

    @PostMapping
    @Transactional
    public ResponseEntity<DetalleTopicoDTO> crearTopico(@RequestBody @Valid CrearTopicoDTO crearTopicoDTO,
                                                        UriComponentsBuilder uriBuilder,
                                                        Authentication authentication) {
        crearValidadores.forEach(v -> v.validate(crearTopicoDTO));

        User usuario = (User) authentication.getPrincipal(); // usuario autenticado
        Course curso = cursoRepo.findById(crearTopicoDTO.cursoId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Curso no encontrado"));

        Topic topico = new Topic(crearTopicoDTO, usuario, curso);
        topicRepo.save(topico);

        var uri = uriBuilder.path("/api/v1/topicos/{id}").buildAndExpand(topico.getId()).toUri();
        return ResponseEntity.created(uri).body(new DetalleTopicoDTO(topico));
    }

    @GetMapping("/all")
    public ResponseEntity<Page<DetalleTopicoDTO>> leerTodosLosTopicos(
            @PageableDefault(size = 5, sort = {"ultimaActualizacion"}, direction = Sort.Direction.ASC) Pageable pageable) {
        Page<Topic> topicos = topicRepo.findAll(pageable);
        return ResponseEntity.ok(topicos.map(DetalleTopicoDTO::new));
    }

    @GetMapping
    public ResponseEntity<Page<DetalleTopicoDTO>> leerTopicosExistentes(
            @PageableDefault(size = 5, sort = {"ultimaActualizacion"}, direction = Sort.Direction.ASC) Pageable pageable) {
        Page<Topic> topicos = topicRepo.findAllByEstadoIsNot(Estado.DELETED, pageable);
        return ResponseEntity.ok(topicos.map(DetalleTopicoDTO::new));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetalleTopicoDTO> leerUnTopico(@PathVariable Long id) {
        Topic topico = topicRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tópico no encontrado"));
        return ResponseEntity.ok(new DetalleTopicoDTO(topico));
    }

    @GetMapping("/{id}/respuestas")
    public ResponseEntity<DetalleRespuestaDTO> leerSolucionTopico(@PathVariable Long id) {
        Response respuesta = respuestaRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Respuesta no encontrada"));
        return ResponseEntity.ok(new DetalleRespuestaDTO(respuesta));
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<DetalleTopicoDTO> actualizarTopico(@RequestBody @Valid ActualizarTopicoDTO actualizarTopicoDTO,
                                                             @PathVariable Long id) {
        Topic topico = topicRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tópico no encontrado"));

        actualizarValidadores.forEach(v -> v.validate(actualizarTopicoDTO));

        if (actualizarTopicoDTO.cursoId() != null) {
            Course curso = cursoRepo.findById(actualizarTopicoDTO.cursoId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Curso no encontrado"));
            topico.actualizarTopicoConCurso(actualizarTopicoDTO, curso);
        } else {
            topico.actualizarTopico(actualizarTopicoDTO);
        }

        return ResponseEntity.ok(new DetalleTopicoDTO(topico));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> eliminarTopico(@PathVariable Long id) {
        Topic topico = topicRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tópico no encontrado"));
        topico.eliminarTopico();
        return ResponseEntity.noContent().build();
    }
}

