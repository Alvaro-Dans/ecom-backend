package com.tfg.account.infrastructure.outbound.persistence.role;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaRoleRepository extends JpaRepository<JpaRole, Long> {

    JpaRole findByName(String name);
}
