package com.tfg.account.infrastructure.outbound.persistence.role;

import com.tfg.account.domain.role.Role;
import com.tfg.account.domain.role.RoleRepository;
import com.tfg.account.infrastructure.outbound.mappers.RoleEntityMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public class PostgreSQLRoleRepository implements RoleRepository {

    private final JpaRoleRepository jpaRoleRepository;
    private final RoleEntityMapper roleEntityMapper;

    public PostgreSQLRoleRepository(JpaRoleRepository jpaRoleRepository, RoleEntityMapper roleEntityMapper) {
        this.jpaRoleRepository = jpaRoleRepository;
        this.roleEntityMapper = roleEntityMapper;
    }

    @Override
    public Role findByName(String name) {
        return roleEntityMapper.toDomain(jpaRoleRepository.findByName(name));
    }
}
