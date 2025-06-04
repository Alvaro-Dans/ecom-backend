package com.tfg.account.infrastructure.outbound.persistence.user;

import com.tfg.account.domain.user.User;
import com.tfg.account.domain.user.UserRepository;
import com.tfg.account.infrastructure.outbound.mappers.UserEntityMapper;
import org.mapstruct.Context;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class PostgreSQLUserRepository implements UserRepository {
    private final JpaUserRepository jpaRepo;
    private final UserEntityMapper mapper;

    public PostgreSQLUserRepository(JpaUserRepository jpaRepo, UserEntityMapper mapper) {
        this.jpaRepo = jpaRepo;
        this.mapper = mapper;
    }

    @Override
    public User save(User user) {
        return mapper.toDomain(jpaRepo.save(mapper.toEntity(user)));
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return jpaRepo.findByEmail(email)
                .map(mapper::toDomain);
    }
}