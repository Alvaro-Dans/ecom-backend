package com.tfg.account.infrastructure.outbound.mappers;

import com.tfg.account.domain.address.Address;
import com.tfg.account.infrastructure.outbound.persistence.address.JpaAddress;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;


@Mapper(componentModel = "spring")
public interface AddressEntityMapper {
    JpaAddress toEntity(Address domain);
    Address toDomain(JpaAddress entity);
}