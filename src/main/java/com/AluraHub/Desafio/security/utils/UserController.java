package com.AluraHub.Desafio.security.utils;
//
//import com.AluraHub.Desafio.entity.user.dto.ActualizarUserDTO;
//import com.AluraHub.Desafio.entity.user.dto.CrearUserDTO;
//import com.AluraHub.Desafio.entity.user.dto.DetallesUserDTO;
//import com.AluraHub.Desafio.entity.user.validations.create.ValidarCrearUsuario;
//import com.AluraHub.Desafio.entity.user.validations.update.IValidarActualizacionUsuario;
//import com.AluraHub.Desafio.security.user.Role;
//import com.AluraHub.Desafio.security.user.User;
//import com.AluraHub.Desafio.security.user.UserRepository;
//import jakarta.validation.Valid;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Sort;
//import org.springframework.data.web.PageableDefault;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.server.ResponseStatusException;
//import org.springframework.web.util.UriComponentsBuilder;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/v1/usuarios")
//public class UserController {
//
//    @Autowired
//    private UserRepository repository;
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    @Autowired
//    private List<ValidarCrearUsuario> crearValidador;
//
//    @Autowired
//    private List<IValidarActualizacionUsuario> actualizarValidador;
//
//    @PostMapping
//    @Transactional
//    public ResponseEntity<DetallesUserDTO> crearUsuario(@RequestBody @Valid CrearUserDTO crearUserDTO,
//                                                        UriComponentsBuilder uriBuilder) {
//        crearValidador.forEach(v -> v.validate(crearUserDTO));
//
//        String hashedPassword = passwordEncoder.encode(crearUserDTO.Password());
//        User usuario = User.builder()
//                .username(crearUserDTO.username())
//                .password(hashedPassword)
//                .firstname(crearUserDTO.firstname())
//                .lastname(crearUserDTO.lastname())
//                .country(crearUserDTO.country())
//                .role(Role.USER)
//                .enabled(true)
//                .build();
//
//        repository.save(usuario);
//        var uri = uriBuilder.path("/api/v1/usuarios/{username}").buildAndExpand(usuario.getUsername()).toUri();
//        return ResponseEntity.created(uri).body(new DetallesUserDTO(usuario));
//    }
//
//    @GetMapping
//    public ResponseEntity<Page<DetallesUserDTO>> leerUsuariosActivos(
//            @PageableDefault(size = 5, sort = {"ultimaActualizacion"}, direction = Sort.Direction.ASC) Pageable pageable) {
//        var pagina = repository.findAllByEnabledTrue(pageable).map(DetallesUserDTO::new);
//        return ResponseEntity.ok(pagina);
//    }
//
//    @GetMapping("/all")
//    public ResponseEntity<Page<DetallesUserDTO>> leerTodosLosUsuarios(
//            @PageableDefault(size = 5, sort = {"ultimaActualizacion"}, direction = Sort.Direction.ASC) Pageable pageable) {
//        var pagina = repository.findAll(pageable).map(DetallesUserDTO::new);
//        return ResponseEntity.ok(pagina);
//    }
//
//
//    @GetMapping("/{id}")
//    public ResponseEntity<DetallesUserDTO> leerUnUsuarioPorId(@PathVariable Long id) {
//        User usuario = repository.findById(id)
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));
//        return ResponseEntity.ok(new DetallesUserDTO(usuario));
//    }
//
//    @GetMapping("/username/{username}")
//    public ResponseEntity<DetallesUserDTO> leerUnUsuarioPorNombre(@PathVariable String username) {
//        User usuario = repository.findByUsername(username)
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));
//        return ResponseEntity.ok(new DetallesUserDTO(usuario));
//    }
//
//    @PutMapping("/{username}")
//    @Transactional
//    public ResponseEntity<DetallesUserDTO> actualizarUsuario(@PathVariable String username, @RequestBody @Valid ActualizarUserDTO actualizarUserDTO) {
//        actualizarValidador.forEach(v -> v.validate(actualizarUserDTO));
//
//        User usuario = repository.findByUsername(username)
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));
//
//        usuario.actualizarUsuario(actualizarUserDTO);
//
//        return ResponseEntity.ok(new DetallesUserDTO(usuario));
//    }
//
//    @DeleteMapping("/{username}")
//    @Transactional
//    public ResponseEntity<Void> eliminarUsuario(@PathVariable String username) {
//        User usuario = repository.findByUsername(username)
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));
//        usuario.eliminarUsuario();
//        return ResponseEntity.noContent().build();
//    }
//
//    // ... resto de métodos igual pero usando UserSecurity
//}
//




import com.AluraHub.Desafio.security.user.User;
import com.AluraHub.Desafio.security.user.UserRepository;
import com.AluraHub.Desafio.security.user.Role;
import com.AluraHub.Desafio.entity.user.dto.ActualizarUserDTO;
import com.AluraHub.Desafio.entity.user.dto.CrearUserDTO;
import com.AluraHub.Desafio.entity.user.dto.DetallesUserDTO;
import com.AluraHub.Desafio.entity.user.validations.create.ValidarCrearUsuario;
import com.AluraHub.Desafio.entity.user.validations.update.IValidarActualizacionUsuario;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/api/v1/usuarios")
public class UserController {

    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private List<ValidarCrearUsuario> crearValidador;

    @Autowired
    private List<IValidarActualizacionUsuario> actualizarValidador;

    @PostMapping
    @Transactional
    public ResponseEntity<DetallesUserDTO> crearUsuario(@RequestBody @Valid CrearUserDTO crearUserDTO,
                                                        UriComponentsBuilder uriBuilder) {
        crearValidador.forEach(v -> v.validate(crearUserDTO));

        String hashedPassword = passwordEncoder.encode(crearUserDTO.Password());
        User usuario = User.builder()
                .username(crearUserDTO.username())
                .password(hashedPassword)
                .firstname(crearUserDTO.firstname())
                .lastname(crearUserDTO.lastname())
                .country(crearUserDTO.country())
                .role(Role.USER)
                .enabled(true)
                .build();

        repository.save(usuario);
        var uri = uriBuilder.path("/api/v1/usuarios/{username}").buildAndExpand(usuario.getUsername()).toUri();
        return ResponseEntity.created(uri).body(new DetallesUserDTO(usuario));
    }

    @GetMapping
    public ResponseEntity<Page<DetallesUserDTO>> leerUsuariosActivos(
            @PageableDefault(size = 5, sort = {"username"}, direction = Sort.Direction.ASC) Pageable pageable) {
        var pagina = repository.findAllByEnabledTrue(pageable).map(DetallesUserDTO::new);
        return ResponseEntity.ok(pagina);
    }

    @GetMapping("/all")
    public ResponseEntity<Page<DetallesUserDTO>> leerTodosLosUsuarios(
            @PageableDefault(size = 5, sort = {"username"}, direction = Sort.Direction.ASC) Pageable pageable) {
        var pagina = repository.findAll(pageable).map(DetallesUserDTO::new);
        return ResponseEntity.ok(pagina);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetallesUserDTO> leerUnUsuarioPorId(@PathVariable Long id) {
        User usuario = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));
        return ResponseEntity.ok(new DetallesUserDTO(usuario));
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<DetallesUserDTO> leerUnUsuarioPorNombre(@PathVariable String username) {
        User usuario = repository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));
        return ResponseEntity.ok(new DetallesUserDTO(usuario));
    }

    @PutMapping("/{username}")
    @Transactional
    public ResponseEntity<DetallesUserDTO> actualizarUsuario(@PathVariable String username, @RequestBody @Valid ActualizarUserDTO actualizarUserDTO) {
        actualizarValidador.forEach(v -> v.validate(actualizarUserDTO));

        User usuario = repository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));

        if (actualizarUserDTO.firstname() != null) usuario.setFirstname(actualizarUserDTO.firstname());
        if (actualizarUserDTO.lastname() != null) usuario.setLastname(actualizarUserDTO.lastname());
        if (actualizarUserDTO.country() != null) usuario.setCountry(actualizarUserDTO.country());
        if (actualizarUserDTO.enabled() != null) usuario.setEnabled(actualizarUserDTO.enabled());

        return ResponseEntity.ok(new DetallesUserDTO(usuario));
    }

    @DeleteMapping("/{username}")
    @Transactional
    public ResponseEntity<Void> eliminarUsuario(@PathVariable String username) {
        User usuario = repository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));
        usuario.setEnabled(false);
        return ResponseEntity.noContent().build();
    }
}
