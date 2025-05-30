package com.tfg.account.infrastructure.outbound.persistence.address;

import com.tfg.account.domain.address.Address;
import com.tfg.account.domain.address.AddressRepository;
import com.tfg.account.infrastructure.outbound.mappers.AddressEntityMapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class PostgreSQLAddressRepository implements AddressRepository {
    private final JpaAddressRepository jpaRepo;
    private final AddressEntityMapper mapper;

    public PostgreSQLAddressRepository(JpaAddressRepository jpaRepo, AddressEntityMapper mapper) {
        this.jpaRepo = jpaRepo;
        this.mapper = mapper;
    }

    @Override
    public Address save(Address address) {
        return mapper.toDomain(jpaRepo.save(mapper.toEntity(address)));
    }

    @Override
    public Optional<Address> findById(Long id) {
        return jpaRepo.findById(id)
                .map(mapper::toDomain);
    }
}