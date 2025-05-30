package com.tfg.account.infrastructure.outbound.persistence.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaUserRepository extends JpaRepository<JpaUser, Long> {
    Optional<JpaUser> findByEmail(String email);
}