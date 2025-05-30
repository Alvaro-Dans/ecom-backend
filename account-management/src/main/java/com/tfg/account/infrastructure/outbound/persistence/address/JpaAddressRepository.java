package com.tfg.account.infrastructure.outbound.persistence.address;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaAddressRepository extends JpaRepository<JpaAddress, Long> {}

