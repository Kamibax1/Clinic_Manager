package com.example.service;

import com.example.exception.AccessDeniedException;
import com.example.exception.ResourceNotFoundException;
import com.example.model.dto.doctor.response.DoctorShortInfoResponse;
import com.example.model.dto.patient.response.PatientFullInformationForUpdatePatientResponse;
import com.example.model.dto.patient.response.PatientShortInfoResponse;
import com.example.model.dto.user.request.CreateUserRequest;
import com.example.model.dto.doctor.response.DoctorFullInformationResponse;
import com.example.model.dto.security.RegisterRequest;
import com.example.model.dto.security.UserResponse;
import com.example.model.dto.patient.response.PatientFullInfoForAdminResponse;
import com.example.model.dto.user.UserFullInfoResponse;
import com.example.model.dto.user.request.UpdateUserFullInfoRequest;
import com.example.model.entity.DoctorEntity;
import com.example.model.entity.PatientEntity;
import com.example.model.entity.RoleEntity;
import com.example.model.entity.UserEntity;
import com.example.model.enums.RoleEnum;
import com.example.repository.DoctorRepository;
import com.example.repository.PatientRepository;
import com.example.repository.RoleRepository;
import com.example.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import com.example.exception.ValidationException;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository, PatientRepository patientRepository, DoctorRepository doctorRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
    }

    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
    public List<UserResponse> findAll() {
        return userRepository.findAll().stream()
                .map(UserResponse::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
    public List<UserResponse> findAllByEnabled(Boolean enabled) {
        return userRepository.findAllByEnabled(enabled).stream()
                .map(UserResponse::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
    public List<UserResponse> findAllByRoleName(RoleEnum roleName) {
        return userRepository.findAllByRoleName(roleName).stream()
                .map(UserResponse::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
    public List<UserResponse> findAllByOrderByUsername() {
        return userRepository.findAllByOrderByUsername().stream()
                .map(UserResponse::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
    public List<UserResponse> findAllByOrderByEmail() {
        return userRepository.findAllByOrderByEmail().stream()
                .map(UserResponse::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
    public List<UserResponse> findAllByUsernameContaining(String username) {
        return userRepository.findAllByUsernameContaining(username).stream()
                .map(UserResponse::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
    public UserFullInfoResponse findFullInfoByUserId(Long id) {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        UserFullInfoResponse fullInfo = new UserFullInfoResponse();
        fullInfo.setId(userEntity.getId());
        fullInfo.setUsername(userEntity.getUsername());
        fullInfo.setEmail(userEntity.getEmail());
        fullInfo.setEnabled(userEntity.isEnabled());
        fullInfo.setRole(userEntity.getRole().getName());

        return findRoleUser(userEntity, fullInfo);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public UserResponse save(CreateUserRequest request) {
        checkData(request.getUsername(), request.getEmail());
        RoleEntity roleEntity = roleRepository.findByName(request.getRole())
                .orElseThrow(() -> new ResourceNotFoundException("Role", "name", request.getRole()));
        UserEntity entity = CreateUserRequest.toEntity(request, roleEntity);
        UserEntity saved = userRepository.save(entity);
        log.info("Пользователь сохранен успешно: username='{}', id={}", saved.getUsername(), saved.getId());
        return UserResponse.fromEntity(saved);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public UserResponse register(RegisterRequest request) throws ValidationException {

        checkData(request.getUsername(), request.getEmail());
        UserEntity entity = RegisterRequest.toEntity(request);
        entity.setPassword(passwordEncoder.encode(request.getPassword()));

        RoleEntity userRole = roleRepository.findByName(RoleEnum.PATIENT)
                .orElseThrow(() -> new ResourceNotFoundException("Role", "name", RoleEnum.PATIENT));
        entity.setRole(userRole);

        UserEntity saved = userRepository.save(entity);
        PatientEntity patient = new PatientEntity();
        patient.setUser(saved);

        patient.setFirstName("Не указано");
        patient.setLastName("Не указано");
        patient.setMiddleName("Не указано");
        patient.setDateOfBirth(LocalDate.now());
        patient.setPhoneNumber("Не указано");
        patient.setGender("Не указано");
        patient.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        patient.setUpdatedAt(new Timestamp(System.currentTimeMillis()));

        patientRepository.save(patient);
        log.info("Пользователь зарегистрировался успешно: username='{}', id={}", saved.getUsername(), saved.getId());
        return UserResponse.fromEntity(saved);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public UserResponse updateEnabled(long id) {
        log.info("Обновление статуса активности пользователя с ID: {}", id);
        UserEntity entity = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        entity.setEnabled(!entity.isEnabled());
        UserEntity saved = userRepository.save(entity);
        log.info("У пользователя с ID: {} статус активности обновлен на: {}", id, saved.isEnabled());
        return UserResponse.fromEntity(saved);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public UserResponse updateRole(long id, RoleEnum role) {
        log.info("Обновление роли у пользователя с ID: {}", id);
        UserEntity entity = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        RoleEntity roleEntity = roleRepository.findByName(role)
                .orElseThrow(() -> new ResourceNotFoundException("Role", "role", role));
        entity.setRole(roleEntity);
        UserEntity saved = userRepository.save(entity);
        log.info("У пользователя с ID: {} роль обновлена на: {}",  id, saved.getRole().getName());
        return UserResponse.fromEntity(saved);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public UserFullInfoResponse updateUserFullInfo(Long userId, UpdateUserFullInfoRequest request) {
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        UserFullInfoResponse fullInfo = new UserFullInfoResponse();
        fullInfo.setUsername(request.getUsername());
        fullInfo.setEmail(request.getEmail());
        fullInfo.setRole(request.getRole());
        fullInfo.setEnabled(request.getEnabled());

        return findRoleUser(userEntity, fullInfo);
    }

    private UserFullInfoResponse findRoleUser(UserEntity userEntity, UserFullInfoResponse fullInfo) {
        DoctorEntity doctorEntity = doctorRepository.findByUser_Id(userEntity.getId()).orElse(null);
        if (doctorEntity != null) {
            fullInfo.setProfileType("DOCTOR");
            fullInfo.setDoctorInfo(DoctorFullInformationResponse.fromEntity(doctorEntity));
            return fullInfo;
        }
        PatientEntity patientEntity = patientRepository.findByUserId(userEntity.getId()).orElse(null);
        if (patientEntity != null) {
            fullInfo.setProfileType("PATIENT");
            fullInfo.setPatientInfo(PatientFullInfoForAdminResponse.fromEntity(patientEntity));
            return fullInfo;
        }
        fullInfo.setProfileType("ADMIN");
        return fullInfo;
    }

    public DoctorShortInfoResponse findDoctorShortInfoByUsername(String username) {
        UserEntity userEntity = CheckUsernameOwnership(username);
        DoctorEntity doctorEntity = doctorRepository.findByUser_Id(userEntity.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Doctor", "ID", userEntity.getId()));

        return DoctorShortInfoResponse.fromEntity(doctorEntity);
    }

    public DoctorFullInformationResponse findDoctorFullInfoByUsername(String username) {
        UserEntity userEntity = CheckUsernameOwnership(username);
        DoctorEntity doctorEntity = doctorRepository.findByUser_Id(userEntity.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Doctor", "ID", userEntity.getId()));

        return DoctorFullInformationResponse.fromEntity(doctorEntity);
    }

    public PatientShortInfoResponse findPatientShortInfoByUsername(String username) {
        UserEntity userEntity = CheckUsernameOwnership(username);
        PatientEntity patientEntity = patientRepository.findByUserId(userEntity.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Patient", "ID", userEntity.getId()));

        return PatientShortInfoResponse.fromEntity(patientEntity);
    }

    public PatientFullInformationForUpdatePatientResponse findPatientFullInformationByUsername(String username) {
        UserEntity userEntity = CheckUsernameOwnership(username);
        PatientEntity patientEntity = patientRepository.findByUserId(userEntity.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Patient", "ID", userEntity.getId()));

        return PatientFullInformationForUpdatePatientResponse.fromEntity(patientEntity);
    }

    public UserResponse findByUsername(String username) {
        UserEntity userEntity = userRepository.findByUsername(username);
        if(userEntity == null) {
            throw new ResourceNotFoundException("User", "username", username);
        }
        return UserResponse.fromEntity(userEntity);
    }

    public boolean checkPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    private UserEntity CheckUsernameOwnership(String username) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AccessDeniedException("User not authenticated");
        }

        String currentUsername = authentication.getName();

        if (!currentUsername.equals(username)) {
            throw new AccessDeniedException("You can only access your own data");
        }

        UserEntity userEntity = userRepository.findByUsername(username);
        if (userEntity == null) {
            throw new ResourceNotFoundException("User", "username", username);
        }
        return userEntity;
    }

    private void checkData(String username, String email) {
        if(userRepository.existsByUsername(username)) {
            log.warn("Регистрация не удалась: username '{}' уже существует", username);
            throw new ValidationException("Username already exists");
        }
        if(userRepository.existsByEmail(email)) {
            log.warn("Регистрация не удалась: email '{}' уже существует", email);
            throw new ValidationException("Email already exists");
        }
    }
}
