package com.AluraHub.Desafio.entity.topic.repository;

import com.AluraHub.Desafio.entity.topic.Estado;
import com.AluraHub.Desafio.entity.topic.Topic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicRepo extends JpaRepository<Topic, Long> {

    Page<Topic> findAll(Pageable pageable);

    Page<Topic> findAllByEstadoIsNot(Estado estado, Pageable pageable);

    Boolean existByTituloAndMensaje(String titulo, String mensaje);

    Topic findByTitulo(String titulo);

}
