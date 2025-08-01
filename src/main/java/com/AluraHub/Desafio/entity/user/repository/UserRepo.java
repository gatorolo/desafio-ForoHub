package com.AluraHub.Desafio.entity.user.repository;

import com.AluraHub.Desafio.entity.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

    UserDetails findByUsername(String username);
    Page<User> findAllEnabledTrue(Pageable pageable);
    User getReferenceById(Long id);
    Page<User> findAll(Pageable pageable);
    User getReferenceByUsername(String username);
    Boolean existByUsername(String username);
}
