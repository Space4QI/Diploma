package org.example.services;

import org.example.Dto.UserDTO;
import org.example.mappers.UserMapper;
import org.example.models.User;
import org.example.repositories.UserRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Cacheable("users")
    public List<UserDTO> getAll() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<UserDTO> getById(UUID id) {
        return userRepository.findById(id).map(userMapper::toDTO);
    }

    public Optional<UserDTO> getByPhone(String phone) {
        return userRepository.findByPhone(phone).map(userMapper::toDTO);
    }

    public UserDTO save(UserDTO dto) {
        User user = userRepository.save(userMapper.toEntity(dto));
        return userMapper.toDTO(user);
    }

    public void delete(UUID id) {
        userRepository.deleteById(id);
    }

    public UserDTO update(UUID id, UserDTO dto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setName(dto.getName());
        user.setNickname(dto.getNickname());
        user.setRole(dto.getRole());
        user.setPoints(dto.getPoints());
        user.setEventCount(dto.getEventCount());
        user.setAvatarUri(dto.getAvatarUri());

        return userMapper.toDTO(userRepository.save(user));
    }
}