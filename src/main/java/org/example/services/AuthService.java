package org.example.services;

import org.example.models.Role;
import org.example.models.User;
import org.example.repositories.UserRepository;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User register(String phone, String password, String name, String nickname) {
        if (userRepository.findByPhone(phone).isPresent()) {
            throw new IllegalArgumentException("User already exists");
        }
        User user = new User();
        user.setId(UUID.randomUUID());
        user.setPhone(phone);
        user.setPassword(passwordEncoder.encode(password));
        user.setName(name);
        user.setNickname(nickname);
        user.setRole(Role.USER);
        user.setPoints(0);
        user.setEventCount(0);
        return userRepository.save(user);
    }

    public User login(String phone, String password) {
        User user = userRepository.findByPhone(phone)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException("Invalid password");
        }
        return user;
    }
}