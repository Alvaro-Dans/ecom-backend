package com.tfg.account.infrastructure.inbound.mapper;

import com.tfg.account.domain.address.Address;
import com.tfg.account.domain.user.User;
import com.tfg.account.infrastructure.inbound.rest.dto.AddressDto;
import com.tfg.account.infrastructure.inbound.rest.dto.response.UserInfoDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ControllerMapper {
    UserInfoDto toUserInfoDto(User user);
    AddressDto toDto(Address address);
    Address toAddress(AddressDto dto);
}