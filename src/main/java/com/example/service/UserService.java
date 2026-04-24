package com.example.service;

import com.example.exception.ResourceNotFoundException;
import com.example.model.dto.security.RegisterRequest;
import com.example.model.dto.security.UserResponse;
import com.example.model.entity.RoleEntity;
import com.example.model.entity.UserEntity;
import com.example.model.enums.RoleEnum;
import com.example.repository.RoleRepository;
import com.example.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.exception.ValidationException;
import java.util.List;

@Slf4j
@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    @Transactional
    public UserResponse register(RegisterRequest request) throws ValidationException {

        if(userRepository.existsByUsername(request.getUsername())) {
            log.warn("Регистрация не удалась: username '{}' уже существует", request.getUsername());
            throw new ValidationException("Username already exists");
        }
        if(userRepository.existsByEmail(request.getEmail())) {
            log.warn("Регистрация не удалась: email '{}' уже существует", request.getEmail());
            throw new ValidationException("Email already exists");
        }

        UserEntity entity = RegisterRequest.toEntity(request);

        entity.setPassword(passwordEncoder.encode(request.getPassword()));

        RoleEntity userRole = roleRepository.findByName(RoleEnum.PATIENT);
        if(userRole == null) {
            log.error("Роли 'PATIENT' нету в базе данных");
            throw new RuntimeException("User does not exist");
        }
        entity.setRole(userRole);

        UserEntity saved = userRepository.save(entity);
        log.info("Пользователь зарегистрировался успешно: username='{}', id={}", saved.getUsername(), saved.getId());
        return UserResponse.fromEntity(saved);
    }

    public UserResponse findByUsername(String username) {
        UserEntity userEntity = userRepository.findByUsername(username);
        if(userEntity == null) {
            throw new ResourceNotFoundException("User", "username", username);
        }
        return UserResponse.fromEntity(userEntity);
    }

    public List<UserResponse> findAll() {
        return userRepository.findAll().stream()
                .map(UserResponse::fromEntity)
                .toList();
    }

    public boolean checkPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}
