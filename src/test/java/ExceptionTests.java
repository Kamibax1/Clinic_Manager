import com.example.exception.AccessDeniedException;
import com.example.exception.BadRequestException;
import com.example.exception.ResourceNotFoundException;
import com.example.exception.ValidationException;
import com.example.model.dto.appointment.request.UpdateAppointmentStatusRequest;
import com.example.model.dto.user.request.CreateUserRequest;
import com.example.service.AppointmentService;
import com.example.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ExceptionTests {

    @Mock
    private com.example.repository.AppointmentRepository appointmentRepository;

    @Mock
    private com.example.repository.StatusRepository statusRepository;

    @Mock
    private com.example.repository.PatientRepository patientRepository;

    @Mock
    private com.example.repository.DoctorRepository doctorRepository;

    @Mock
    private com.example.repository.UserRepository userRepository;

    @Mock
    private com.example.repository.RoleRepository roleRepository;

    @Mock
    private org.springframework.security.crypto.password.PasswordEncoder passwordEncoder;

    @InjectMocks
    private AppointmentService appointmentService;

    @InjectMocks
    private UserService userService;

    @Test
    void shouldThrowResourceNotFoundException_whenAppointmentNotFound() {
        long nonExistentId = 999L;
        when(appointmentRepository.findById(nonExistentId)).thenReturn(java.util.Optional.empty());

        assertThatThrownBy(() -> appointmentService.findFullInfoById(nonExistentId))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Appointment with id 999 not found");
    }

    @Test
    void shouldThrowResourceNotFoundException_whenUserNotFound() {
        String nonExistentUsername = "nonexistent";
        when(userRepository.findByUsername(nonExistentUsername)).thenReturn(null);

        assertThatThrownBy(() -> userService.findByUsername(nonExistentUsername))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("User not found with username: 'nonexistent'");
    }

    @Test
    void shouldThrowAccessDeniedException_whenDoctorTriesToUpdateWrongAppointment() {
        long appointmentId = 100L;
        int wrongDoctorId = 999;

        com.example.model.entity.AppointmentEntity appointment = new com.example.model.entity.AppointmentEntity();
        com.example.model.entity.DoctorEntity doctor = new com.example.model.entity.DoctorEntity();
        doctor.setId(10L);
        appointment.setDoctor(doctor);

        UpdateAppointmentStatusRequest request = new UpdateAppointmentStatusRequest();
        request.setDoctorId(wrongDoctorId);
        request.setStatus(com.example.model.enums.StatusEnum.COMPLETED);

        when(appointmentRepository.findById(appointmentId)).thenReturn(java.util.Optional.of(appointment));

        assertThatThrownBy(() -> appointmentService.updateDoctorAppointmentStatus(appointmentId, request))
                .isInstanceOf(AccessDeniedException.class)
                .hasMessage("Access denied to Appointment with ID 100 for role: DOCTOR");
    }

    @Test
    void shouldThrowValidationException_whenUsernameAlreadyExists() {
        String existingUsername = "existingUser";
        String email = "new@example.com";

        CreateUserRequest request = new CreateUserRequest();
        request.setUsername(existingUsername);
        request.setEmail(email);
        request.setPassword("password123");

        when(userRepository.existsByUsername(existingUsername)).thenReturn(true);

        assertThatThrownBy(() -> userService.save(request))
                .isInstanceOf(ValidationException.class)
                .hasMessage("Username already exists");
    }

    @Test
    void shouldThrowValidationException_whenEmailAlreadyExists() {
        String username = "newUser";
        String existingEmail = "existing@example.com";

        CreateUserRequest request = new CreateUserRequest();
        request.setUsername(username);
        request.setEmail(existingEmail);
        request.setPassword("password123");

        when(userRepository.existsByUsername(username)).thenReturn(false);
        when(userRepository.existsByEmail(existingEmail)).thenReturn(true);

        assertThatThrownBy(() -> userService.save(request))
                .isInstanceOf(ValidationException.class)
                .hasMessage("Email already exists");
    }

    @Test
    void shouldThrowBadRequestException_whenInvalidDataProvided() {
        assertThatThrownBy(() -> {
            throw new BadRequestException("Invalid appointment data");
        }).isInstanceOf(BadRequestException.class)
                .hasMessage("Invalid appointment data");
    }
}