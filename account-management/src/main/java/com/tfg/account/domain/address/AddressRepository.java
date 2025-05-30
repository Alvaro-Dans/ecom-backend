package com.tfg.account.domain.address;

import java.util.Optional;

public interface AddressRepository {
    Address save(Address address);
    Optional<Address> findById(Long id);
}