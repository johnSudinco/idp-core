package com.idp_core.idp_core.application.usecase;

import com.idp_core.idp_core.domain.model.Role;
import com.idp_core.idp_core.domain.model.User;
import com.idp_core.idp_core.domain.model.UserRole;
import com.idp_core.idp_core.domain.port.repository.RoleRepositoryPort;
import com.idp_core.idp_core.domain.port.repository.UserRepositoryPort;
import com.idp_core.idp_core.domain.port.repository.UserRoleRepositoryPort;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class RoleUseCase {

    private final UserRepositoryPort userRepository;
    private final RoleRepositoryPort roleRepository;
    private final UserRoleRepositoryPort userRoleRepository;

    public RoleUseCase(UserRepositoryPort userRepository,
                       RoleRepositoryPort roleRepository,
                       UserRoleRepositoryPort userRoleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userRoleRepository = userRoleRepository;
    }

    @Transactional
    public void assignRole(Long userId, Long roleId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no existe"));

        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Rol no existe"));

        if (userRoleRepository.exists(userId, roleId)) {
            return;
        }

        userRoleRepository.save(new UserRole(user, role));
    }


    @Transactional
    public void removeRole(Long userId, Long roleId) {
        userRoleRepository.delete(userId, roleId);
    }
}


