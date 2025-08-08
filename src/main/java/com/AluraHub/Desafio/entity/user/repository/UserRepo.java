package com.AluraHub.Desafio.entity.user.repository;

import com.AluraHub.Desafio.entity.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
    Optional<User> findById(Long id);
    Page<User> findAllByEnabledTrue(Pageable pageable);
    User getReferenceById(Long id);
    Page<User> findAll(Pageable pageable);
    User getReferenceByUsername(String username);
    Boolean existsByUsername(String username);
//    Optional<User> findByUsername(String username);
   // boolean existsByUsername(String username);
   // Page<User> findAllByEnabledTrue(Pageable pageable);

}
