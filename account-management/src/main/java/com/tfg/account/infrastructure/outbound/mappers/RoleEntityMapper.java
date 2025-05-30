package com.tfg.account.infrastructure.outbound.mappers;

import com.tfg.account.domain.role.Role;
import com.tfg.account.infrastructure.outbound.persistence.role.JpaRole;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleEntityMapper {

    /** Convierte entidad de dominio Role a entidad JPA JpaRole */
    JpaRole toEntity(Role domain);

    /** Convierte entidad JPA JpaRole a entidad de dominio Role */
    Role toDomain(JpaRole entity);
}