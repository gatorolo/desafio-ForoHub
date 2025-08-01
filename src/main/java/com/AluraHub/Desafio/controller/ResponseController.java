package com.AluraHub.Desafio.controller;

import com.AluraHub.Desafio.entity.response.Response;
import com.AluraHub.Desafio.entity.response.dto.ActualizarRespuestaDTO;
import com.AluraHub.Desafio.entity.response.dto.CrearRespuestaDTO;
import com.AluraHub.Desafio.entity.response.dto.DetalleRespuestaDTO;
import com.AluraHub.Desafio.entity.response.repository.ResponseRepo;
import com.AluraHub.Desafio.entity.response.validations.create.ValidarRespuestaCreada;
import com.AluraHub.Desafio.entity.response.validations.update.ValidarRespuestaActualizada;
import com.AluraHub.Desafio.entity.topic.Estado;
import com.AluraHub.Desafio.entity.topic.Topic;
import com.AluraHub.Desafio.entity.topic.repository.TopicRepo;
import com.AluraHub.Desafio.entity.user.User;
import com.AluraHub.Desafio.entity.user.repository.UserRepo;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/respuesta")
public class ResponseController {

    @Autowired
    private TopicRepo topicRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ResponseRepo responseRepo;

    @Autowired
    List<ValidarRespuestaCreada> crearValidadores;

    @Autowired
    List<ValidarRespuestaActualizada> actualizarValidadores;


    @PostMapping
    @Transactional
    public ResponseEntity<DetalleRespuestaDTO> crearRespuesta(@RequestBody @Valid CrearRespuestaDTO crearRespuestaDTO,
                                                              UriComponentsBuilder uriBuilder) {
        crearValidadores.forEach(v -> v.validate(crearRespuestaDTO));

        User usuario = userRepo.getReferenceById(crearRespuestaDTO.usuarioId());
        Topic topico = topicRepo.findById(crearRespuestaDTO.topicoId()).get();

        var respuesta = new Response(crearRespuestaDTO, usuario, topico);
        responseRepo.save(respuesta);

         var uri = uriBuilder.path("/respuesta/{id}").buildAndExpand(respuesta.getId()).toUri();
         return ResponseEntity.created(uri).body(new DetalleRespuestaDTO(respuesta));
    }

    @GetMapping("/topico/{id}")
    public ResponseEntity<Page<DetalleRespuestaDTO>> leerRespuestaTopico(@PageableDefault(size = 5,sort = {"ultimaActualizacion"},
                               direction = Sort.Direction.ASC)Pageable pageable, @PathVariable Long topicoId) {
        var pagina = responseRepo.findAllByTopicoId(topicoId,pageable).map(DetalleRespuestaDTO::new);
                return ResponseEntity.ok(pagina);
    }

    @GetMapping("/usuario/{nombreUsuario}")
    public ResponseEntity<Page<DetalleRespuestaDTO>> leerRespuestasUs(@PageableDefault(size = 5, sort = {"ultimaActualizacion"},
    direction = Sort.Direction.ASC)Pageable pageable, @PathVariable Long usuarioId) {
        var pagina = responseRepo.findAllByUsuarioId(usuarioId, pageable).map(DetalleRespuestaDTO::new);
        return ResponseEntity.ok(pagina);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetalleRespuestaDTO> leerUnaRespuesta(@PathVariable Long id) {
        Response respuesta = responseRepo.getReferenceById(id);

        var datosRespuesta = new DetalleRespuestaDTO(
                respuesta.getId(),
                respuesta.getMensaje(),
                respuesta.getFechaCreacion(),
                respuesta.getUltimaActualizacion(),
                respuesta.isSolucion(),
                respuesta.isBorrado(),
                respuesta.getUsuario().getId(),
                respuesta.getUsuario().getUsername(),
                respuesta.getTopico().getId(),
                respuesta.getTopico().getTitulo()
        );
                return ResponseEntity.ok(datosRespuesta);
    }

    @PutMapping("{id}")
    @Transactional
    public ResponseEntity<DetalleRespuestaDTO> actualizarRespuesta(@RequestBody @Valid ActualizarRespuestaDTO actualizarRespuestaDTO, @PathVariable Long id) {
        actualizarValidadores.forEach(v -> v.validate(actualizarRespuestaDTO, id));
        Response respuesta = responseRepo.getReferenceById(id);
        respuesta.actualizarRespuesta(actualizarRespuestaDTO);

        if(actualizarRespuestaDTO.solucion()) {
            var temaResuelto = topicRepo.getReferenceById(respuesta.getTopico().getId());
            temaResuelto.setEstado(Estado.CLOSED);
        }

        var datosRespuesta = new DetalleRespuestaDTO(
                respuesta.getId(),
                respuesta.getMensaje(),
                respuesta.getFechaCreacion(),
                respuesta.getUltimaActualizacion(),
                respuesta.isSolucion(),
                respuesta.isBorrado(),
                respuesta.getUsuario().getId(),
                respuesta.getUsuario().getUsername(),
                respuesta.getTopico().getId(),
                respuesta.getTopico().getTitulo()
        );
              return ResponseEntity.ok(datosRespuesta);

    }


    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> borrarRespuesta(@PathVariable Long id) {
        Response respuesta = responseRepo.getReferenceById(id);
        respuesta.eliminarRespuesta();
        return ResponseEntity.noContent().build();
    }

}
