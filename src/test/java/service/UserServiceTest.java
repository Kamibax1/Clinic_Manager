package service;

import com.example.exception.AccessDeniedException;
import com.example.exception.ResourceNotFoundException;
import com.example.exception.ValidationException;
import com.example.model.dto.patient.response.PatientShortInfoResponse;
import com.example.model.dto.security.RegisterRequest;
import com.example.model.dto.security.UserResponse;
import com.example.model.dto.user.request.CreateUserRequest;
import com.example.model.dto.user.UserFullInfoResponse;
import com.example.model.entity.*;
import com.example.model.enums.RoleEnum;
import com.example.repository.DoctorRepository;
import com.example.repository.PatientRepository;
import com.example.repository.RoleRepository;
import com.example.repository.UserRepository;
import com.example.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private DoctorRepository doctorRepository;

    @Mock
    private Authentication authentication;

    @Mock
    private SecurityContext securityContext;

    @InjectMocks
    private UserService userService;

    private UserEntity testUser;
    private RoleEntity adminRole;
    private RoleEntity doctorRole;
    private RoleEntity patientRole;

    @BeforeEach
    void setUp() {
        adminRole = new RoleEntity(1L, RoleEnum.ADMIN);
        doctorRole = new RoleEntity(2L, RoleEnum.DOCTOR);
        patientRole = new RoleEntity(3L, RoleEnum.PATIENT);

        testUser = new UserEntity();
        testUser.setId(1L);
        testUser.setUsername("testuser");
        testUser.setEmail("test@example.com");
        testUser.setPassword("encodedPassword");
        testUser.setEnabled(true);
        testUser.setRole(patientRole);
        testUser.setCreatedAt(new Timestamp(System.currentTimeMillis()));
    }

    @Test
    void findAll_shouldReturnListOfUserResponses() {
        when(userRepository.findAll()).thenReturn(List.of(testUser));

        List<UserResponse> result = userService.findAll();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getUsername()).isEqualTo("testuser");
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void findAllByEnabled_shouldReturnEnabledUsers() {
        when(userRepository.findAllByEnabled(true)).thenReturn(List.of(testUser));

        List<UserResponse> result = userService.findAllByEnabled(true);

        assertThat(result).hasSize(1);
        verify(userRepository, times(1)).findAllByEnabled(true);
    }

    @Test
    void findAllByRoleName_shouldReturnUsersWithGivenRole() {
        when(userRepository.findAllByRoleName(RoleEnum.PATIENT)).thenReturn(List.of(testUser));

        List<UserResponse> result = userService.findAllByRoleName(RoleEnum.PATIENT);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getRole()).isEqualTo(RoleEnum.PATIENT);
    }

    @Test
    void findFullInfoByUserId_forPatient_shouldReturnPatientInfo() {
        PatientEntity patientEntity = new PatientEntity();
        patientEntity.setId(1L);
        patientEntity.setFirstName("Иван");
        patientEntity.setLastName("Петров");
        patientEntity.setUser(testUser);

        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(patientRepository.findByUserId(1L)).thenReturn(Optional.of(patientEntity));
        when(doctorRepository.findByUser_Id(1L)).thenReturn(Optional.empty());

        UserFullInfoResponse result = userService.findFullInfoByUserId(1L);

        assertThat(result).isNotNull();
        assertThat(result.getProfileType()).isEqualTo("PATIENT");
        assertThat(result.getPatientInfo()).isNotNull();
    }

    @Test
    void save_shouldCreateUserSuccessfully() {
        CreateUserRequest request = new CreateUserRequest();
        request.setUsername("newuser");
        request.setEmail("new@example.com");
        request.setPassword("password123");
        request.setEnabled(true);
        request.setRole(RoleEnum.PATIENT);

        when(userRepository.existsByUsername("newuser")).thenReturn(false);
        when(userRepository.existsByEmail("new@example.com")).thenReturn(false);
        when(roleRepository.findByName(RoleEnum.PATIENT)).thenReturn(Optional.of(patientRole));
        when(passwordEncoder.encode("password123")).thenReturn("encodedPassword");
        when(userRepository.save(any(UserEntity.class))).thenAnswer(invocation -> {
            UserEntity saved = invocation.getArgument(0);
            saved.setId(2L);
            return saved;
        });

        UserResponse result = userService.save(request);

        assertThat(result).isNotNull();
        assertThat(result.getUsername()).isEqualTo("newuser");
        verify(userRepository, times(1)).save(any(UserEntity.class));
    }

    @Test
    void register_shouldCreatePatientWithDefaultValues() {
        RegisterRequest request = new RegisterRequest();
        request.setUsername("newpatient");
        request.setEmail("patient@example.com");
        request.setPassword("password123");

        when(userRepository.existsByUsername("newpatient")).thenReturn(false);
        when(userRepository.existsByEmail("patient@example.com")).thenReturn(false);
        when(roleRepository.findByName(RoleEnum.PATIENT)).thenReturn(Optional.of(patientRole));
        when(passwordEncoder.encode("password123")).thenReturn("encodedPassword");
        when(userRepository.save(any(UserEntity.class))).thenAnswer(invocation -> {
            UserEntity saved = invocation.getArgument(0);
            saved.setId(3L);
            return saved;
        });
        when(patientRepository.save(any(PatientEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));

        UserResponse result = userService.register(request);

        assertThat(result).isNotNull();
        assertThat(result.getUsername()).isEqualTo("newpatient");
        verify(patientRepository, times(1)).save(any(PatientEntity.class));
    }

    @Test
    void updateEnabled_shouldToggleUserEnabledStatus() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(userRepository.save(any(UserEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));

        UserResponse result = userService.updateEnabled(1L);

        assertThat(result.isEnabled()).isFalse();
        verify(userRepository, times(1)).save(testUser);
    }

    @Test
    void updateRole_shouldChangeUserRole() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(roleRepository.findByName(RoleEnum.DOCTOR)).thenReturn(Optional.of(doctorRole));
        when(userRepository.save(any(UserEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));

        UserResponse result = userService.updateRole(1L, RoleEnum.DOCTOR);

        assertThat(result.getRole()).isEqualTo(RoleEnum.DOCTOR);
        verify(userRepository, times(1)).save(testUser);
    }

    @Test
    void save_shouldThrowValidationException_whenUsernameAlreadyExists() {
        CreateUserRequest request = new CreateUserRequest();
        request.setUsername("existinguser");
        request.setEmail("new@example.com");
        request.setPassword("password123");

        when(userRepository.existsByUsername("existinguser")).thenReturn(true);

        assertThatThrownBy(() -> userService.save(request))
                .isInstanceOf(ValidationException.class)
                .hasMessageContaining("Username already exists");

        verify(userRepository, never()).save(any(UserEntity.class));
    }

    @Test
    void save_shouldThrowValidationException_whenEmailAlreadyExists() {
        CreateUserRequest request = new CreateUserRequest();
        request.setUsername("newuser");
        request.setEmail("existing@example.com");
        request.setPassword("password123");

        when(userRepository.existsByUsername("newuser")).thenReturn(false);
        when(userRepository.existsByEmail("existing@example.com")).thenReturn(true);

        assertThatThrownBy(() -> userService.save(request))
                .isInstanceOf(ValidationException.class)
                .hasMessageContaining("Email already exists");
    }

    @Test
    void findFullInfoByUserId_shouldThrowResourceNotFoundException_whenUserNotFound() {
        when(userRepository.findById(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.findFullInfoByUserId(999L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("User not found with id: '999'");
    }

    @Test
    void updateEnabled_shouldThrowResourceNotFoundException_whenUserNotFound() {
        when(userRepository.findById(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.updateEnabled(999L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("User not found with id: '999'");
    }

    @Test
    void findDoctorShortInfoByUsername_shouldThrowAccessDeniedException_whenNotOwnUser() {
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.isAuthenticated()).thenReturn(true);
        when(authentication.getName()).thenReturn("otheruser");
        SecurityContextHolder.setContext(securityContext);

        assertThatThrownBy(() -> userService.findDoctorShortInfoByUsername("testuser"))
                .isInstanceOf(AccessDeniedException.class)
                .hasMessageContaining("You can only access your own data");
    }

    @Test
    void save_shouldNotCallRepositorySave_whenValidationFails() {
        CreateUserRequest request = new CreateUserRequest();
        request.setUsername("existing");
        request.setEmail("email@test.com");

        when(userRepository.existsByUsername("existing")).thenReturn(true);

        assertThatThrownBy(() -> userService.save(request))
                .isInstanceOf(ValidationException.class);

        verify(userRepository, never()).save(any(UserEntity.class));
    }

    @Test
    void register_shouldCallPatientRepositorySaveOnlyOnce() {
        RegisterRequest request = new RegisterRequest();
        request.setUsername("newpatient");
        request.setEmail("patient@test.com");
        request.setPassword("pass");

        when(userRepository.existsByUsername(anyString())).thenReturn(false);
        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(roleRepository.findByName(RoleEnum.PATIENT)).thenReturn(Optional.of(patientRole));
        when(passwordEncoder.encode(anyString())).thenReturn("encoded");
        when(userRepository.save(any(UserEntity.class))).thenReturn(testUser);
        when(patientRepository.save(any(PatientEntity.class))).thenReturn(new PatientEntity());

        userService.register(request);

        verify(patientRepository, times(1)).save(any(PatientEntity.class));
    }

    @Test
    void save_shouldEncodePasswordBeforeSaving() {
        CreateUserRequest request = new CreateUserRequest();
        request.setUsername("testuser");
        request.setEmail("test@example.com");
        request.setPassword("rawPassword123");
        request.setEnabled(true);
        request.setRole(RoleEnum.PATIENT);

        ArgumentCaptor<UserEntity> userCaptor = ArgumentCaptor.forClass(UserEntity.class);

        when(userRepository.existsByUsername(anyString())).thenReturn(false);
        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(roleRepository.findByName(RoleEnum.PATIENT)).thenReturn(Optional.of(patientRole));
        when(passwordEncoder.encode("rawPassword123")).thenReturn("encodedPassword123");
        when(userRepository.save(userCaptor.capture())).thenAnswer(invocation -> invocation.getArgument(0));

        userService.save(request);

        UserEntity capturedUser = userCaptor.getValue();
        assertThat(capturedUser.getPassword()).isEqualTo("encodedPassword123");
        verify(passwordEncoder, times(1)).encode("rawPassword123");
    }

    @Test
    void register_shouldSetDefaultPatientValues() {
        RegisterRequest request = new RegisterRequest();
        request.setUsername("newpatient");
        request.setEmail("patient@test.com");
        request.setPassword("pass");

        ArgumentCaptor<PatientEntity> patientCaptor = ArgumentCaptor.forClass(PatientEntity.class);

        when(userRepository.existsByUsername(anyString())).thenReturn(false);
        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(roleRepository.findByName(RoleEnum.PATIENT)).thenReturn(Optional.of(patientRole));
        when(passwordEncoder.encode(anyString())).thenReturn("encoded");
        when(userRepository.save(any(UserEntity.class))).thenReturn(testUser);
        when(patientRepository.save(patientCaptor.capture())).thenReturn(new PatientEntity());

        userService.register(request);

        PatientEntity capturedPatient = patientCaptor.getValue();
        assertThat(capturedPatient.getFirstName()).isEqualTo("Не указано");
        assertThat(capturedPatient.getLastName()).isEqualTo("Не указано");
        assertThat(capturedPatient.getGender()).isEqualTo("Не указано");
    }

    @Test
    void findAllByOrderByUsername_shouldReturnSortedUsers() {
        when(userRepository.findAllByOrderByUsername()).thenReturn(List.of(testUser));

        List<UserResponse> result = userService.findAllByOrderByUsername();

        assertThat(result).hasSize(1);
        verify(userRepository, times(1)).findAllByOrderByUsername();
    }

    @Test
    void findAllByOrderByEmail_shouldReturnSortedUsers() {
        when(userRepository.findAllByOrderByEmail()).thenReturn(List.of(testUser));

        List<UserResponse> result = userService.findAllByOrderByEmail();

        assertThat(result).hasSize(1);
        verify(userRepository, times(1)).findAllByOrderByEmail();
    }

    @Test
    void findAllByUsernameContaining_shouldReturnMatchingUsers() {
        when(userRepository.findAllByUsernameContaining("test")).thenReturn(List.of(testUser));

        List<UserResponse> result = userService.findAllByUsernameContaining("test");

        assertThat(result).hasSize(1);
        verify(userRepository, times(1)).findAllByUsernameContaining("test");
    }

    @Test
    void findAllByUsernameContaining_shouldReturnEmptyList_whenNoMatch() {
        when(userRepository.findAllByUsernameContaining("nonexistent")).thenReturn(List.of());

        List<UserResponse> result = userService.findAllByUsernameContaining("nonexistent");

        assertThat(result).isEmpty();
    }

    @Test
    void findFullInfoByUserId_forDoctor_shouldReturnDoctorInfo() {
        DoctorEntity doctorEntity = new DoctorEntity();
        doctorEntity.setId(10L);
        doctorEntity.setFirstName("Алексей");
        doctorEntity.setLastName("Иванов");
        doctorEntity.setUser(testUser);
        doctorEntity.setSpecializations(new HashSet<>());

        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(doctorRepository.findByUser_Id(1L)).thenReturn(Optional.of(doctorEntity));

        UserFullInfoResponse result = userService.findFullInfoByUserId(1L);

        assertThat(result.getProfileType()).isEqualTo("DOCTOR");
        assertThat(result.getDoctorInfo()).isNotNull();
    }

    @Test
    void findFullInfoByUserId_forAdmin_shouldReturnAdminProfile() {
        testUser.setRole(new RoleEntity(1L, RoleEnum.ADMIN));

        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(doctorRepository.findByUser_Id(1L)).thenReturn(Optional.empty());
        when(patientRepository.findByUserId(1L)).thenReturn(Optional.empty());

        UserFullInfoResponse result = userService.findFullInfoByUserId(1L);

        assertThat(result.getProfileType()).isEqualTo("ADMIN");
    }

    @Test
    void checkPassword_shouldReturnTrue_forValidPassword() {
        String rawPassword = "password123";
        String encodedPassword = "encodedPassword";

        when(passwordEncoder.matches(rawPassword, encodedPassword)).thenReturn(true);

        boolean result = userService.checkPassword(rawPassword, encodedPassword);

        assertThat(result).isTrue();
    }

    @Test
    void checkPassword_shouldReturnFalse_forInvalidPassword() {
        String rawPassword = "wrong";
        String encodedPassword = "encodedPassword";

        when(passwordEncoder.matches(rawPassword, encodedPassword)).thenReturn(false);

        boolean result = userService.checkPassword(rawPassword, encodedPassword);

        assertThat(result).isFalse();
    }

    @Test
    void findByUsername_shouldReturnUserResponse_whenUserExists() {
        when(userRepository.findByUsername("testuser")).thenReturn(testUser);

        UserResponse result = userService.findByUsername("testuser");

        assertThat(result).isNotNull();
        assertThat(result.getUsername()).isEqualTo("testuser");
    }

    @Test
    void findByUsername_shouldThrowResourceNotFoundException_whenUserNotFound() {
        when(userRepository.findByUsername("nonexistent")).thenReturn(null);

        assertThatThrownBy(() -> userService.findByUsername("nonexistent"))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void findPatientShortInfoByUsername_shouldReturnPatientInfo() {
        PatientEntity patientEntity = new PatientEntity();
        patientEntity.setId(1L);
        patientEntity.setFirstName("Иван");
        patientEntity.setLastName("Петров");
        patientEntity.setUser(testUser);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.isAuthenticated()).thenReturn(true);
        when(authentication.getName()).thenReturn("testuser");
        SecurityContextHolder.setContext(securityContext);

        when(userRepository.findByUsername("testuser")).thenReturn(testUser);
        when(patientRepository.findByUserId(1L)).thenReturn(Optional.of(patientEntity));

        PatientShortInfoResponse result = userService.findPatientShortInfoByUsername("testuser");

        assertThat(result).isNotNull();
        assertThat(result.getFirstName()).isEqualTo("Иван");
    }

    @Test
    void findPatientFullInformationByUsername_shouldReturnFullPatientInfo() {
        PatientEntity patientEntity = new PatientEntity();
        patientEntity.setId(1L);
        patientEntity.setFirstName("Иван");
        patientEntity.setLastName("Петров");
        patientEntity.setUser(testUser);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.isAuthenticated()).thenReturn(true);
        when(authentication.getName()).thenReturn("testuser");
        SecurityContextHolder.setContext(securityContext);

        when(userRepository.findByUsername("testuser")).thenReturn(testUser);
        when(patientRepository.findByUserId(1L)).thenReturn(Optional.of(patientEntity));

        var result = userService.findPatientFullInformationByUsername("testuser");

        assertThat(result).isNotNull();
        assertThat(result.getFirstName()).isEqualTo("Иван");
    }
}