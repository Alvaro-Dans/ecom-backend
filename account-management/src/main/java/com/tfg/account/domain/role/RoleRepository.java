package com.tfg.account.domain.role;

public interface RoleRepository {

    Role findByName(String name);
}
