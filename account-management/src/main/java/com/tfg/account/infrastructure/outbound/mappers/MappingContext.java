package com.tfg.account.infrastructure.outbound.mappers;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Getter
public class MappingContext {
    private final UserEntityMapper userEntityMapper;
    private final AddressEntityMapper addressEntityMapper;
    private final RoleEntityMapper roleEntityMapper;

    @Autowired
    public MappingContext(UserEntityMapper userEntityMapper, AddressEntityMapper addressEntityMapper, RoleEntityMapper roleEntityMapper) {
        this.userEntityMapper = userEntityMapper;
        this.addressEntityMapper = addressEntityMapper;
        this.roleEntityMapper = roleEntityMapper;
    }
}