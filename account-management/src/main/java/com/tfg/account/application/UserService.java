package com.tfg.account.application;

import com.tfg.account.domain.address.Address;
import com.tfg.account.domain.role.Role;
import com.tfg.account.domain.role.RoleRepository;
import com.tfg.account.domain.user.User;
import com.tfg.account.domain.user.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public UserService(UserRepository userRepo, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    public void register(String firstName, String lastName, String email, String rawPassword) {
        String hash = passwordEncoder.encode(rawPassword);
        User user = new User(null, firstName, lastName, email, hash, null, List.of(roleRepository.findByName("ROLE_USER")));
        userRepo.save(user);
    }

    public Optional<User> findByEmail(String email) {
        return userRepo.findByEmail(email);
    }

    public User createOrUpdateAddress(String email, Address newAddress) {
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setAddress(newAddress);
        return userRepo.save(user);
    }

    public void resetPassword(String email, String currentPassword, String newPassword) {
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (!passwordEncoder.matches(currentPassword, user.getPasswordHash())) {
            throw new RuntimeException("Invalid current password");
        }
        user.setPasswordHash(passwordEncoder.encode(newPassword));
        userRepo.save(user);
    }
}
