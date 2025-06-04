package com.tfg.account.infrastructure.outbound.mappers;


import com.tfg.account.domain.user.User;
import com.tfg.account.infrastructure.outbound.persistence.user.JpaUser;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring", uses = {AddressEntityMapper.class, RoleEntityMapper.class})
public interface UserEntityMapper {

    JpaUser toEntity(User domain);

    User toDomain(JpaUser entity);
}
