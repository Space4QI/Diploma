package org.example.services;

import org.example.Dto.UserDTO;
import org.example.mappers.UserMapper;
import org.example.models.User;
import org.example.repositories.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public CustomUserDetailsService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String nickname) throws UsernameNotFoundException {
        User userEntity = userRepository.findByNickname(nickname)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        UserDTO userDto = userMapper.toDTO(userEntity);

        return new org.springframework.security.core.userdetails.User(
                userDto.getNickname(),
                userDto.getPassword(),
                Collections.singleton(new SimpleGrantedAuthority("ROLE_" + userDto.getRole().name()))
        );
    }
}