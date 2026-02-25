package com.idp_core.idp_core.infrastructure.adapter.repository.impl;

import com.idp_core.idp_core.domain.model.User;
import com.idp_core.idp_core.domain.port.repository.UserRepositoryPort;
import com.idp_core.idp_core.infrastructure.adapter.mapper.UserMapper;
import com.idp_core.idp_core.infrastructure.adapter.repository.jpa.JpaUserRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserRepositoryImpl implements UserRepositoryPort {

    private final JpaUserRepository jpaRepository;
    private final UserMapper userMapper;

    public UserRepositoryImpl(JpaUserRepository jpaRepository, UserMapper userMapper) {
        this.jpaRepository = jpaRepository;
        this.userMapper = userMapper;
    }

    @Override
    public User save(User user) {
        var entity = userMapper.toEntity(user);         // dominio -> entidad
        var saved = jpaRepository.save(entity);        // persistir entidad
        return userMapper.toDomain(saved);             // entidad -> dominio
    }

    @Override
    public Optional<User> findById(Long id) {
        return jpaRepository.findById(id)
                .map(userMapper::toDomain);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return jpaRepository.findByUsername(username)
                .map(userMapper::toDomain);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return jpaRepository.findByEmail(email)
                .map(userMapper::toDomain);
    }

    @Override
    public User getReferenceById(Long id) {
        var entity = jpaRepository.getReferenceById(id);
        return userMapper.toDomain(entity);
    }
}
