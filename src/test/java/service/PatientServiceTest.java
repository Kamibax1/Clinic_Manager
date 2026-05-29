package service;

import com.example.exception.ResourceNotFoundException;
import com.example.model.dto.patient.request.UpdatePatientFullInformationRequest;
import com.example.model.dto.patient.response.PatientShortInfoResponse;
import com.example.model.dto.patient.response.UpdatePatientFullInformationResponse;
import com.example.model.entity.PatientEntity;
import com.example.model.entity.RoleEntity;
import com.example.model.entity.UserEntity;
import com.example.model.enums.RoleEnum;
import com.example.repository.PatientRepository;
import com.example.repository.UserRepository;
import com.example.service.PatientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PatientServiceTest {

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private PatientService patientService;

    private PatientEntity testPatient;
    private UserEntity testUser;

    @BeforeEach
    void setUp() {
        RoleEntity patientRole = new RoleEntity(3L, RoleEnum.PATIENT);

        testUser = new UserEntity();
        testUser.setId(10L);
        testUser.setUsername("patient1");
        testUser.setEmail("patient@example.com");
        testUser.setRole(patientRole);

        testPatient = new PatientEntity();
        testPatient.setId(1L);
        testPatient.setFirstName("Иван");
        testPatient.setLastName("Петров");
        testPatient.setMiddleName("Сергеевич");
        testPatient.setDateOfBirth(LocalDate.of(1990, 5, 15));
        testPatient.setGender("Мужской");
        testPatient.setPhoneNumber("+7 (926) 123-45-67");
        testPatient.setUser(testUser);
    }

    @Test
    void findAllShortInfo_shouldReturnListOfPatients() {
        when(patientRepository.findAll()).thenReturn(List.of(testPatient));

        List<PatientShortInfoResponse> result = patientService.findAllShortInfo();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getFirstName()).isEqualTo("Иван");
        assertThat(result.get(0).getLastName()).isEqualTo("Петров");
        verify(patientRepository, times(1)).findAll();
    }

    @Test
    void findAllShortInfoByName_shouldReturnPatientsMatchingName() {
        when(patientRepository.findAllByName("Петров")).thenReturn(List.of(testPatient));

        List<PatientShortInfoResponse> result = patientService.findAllShortInfoByName("Петров");

        assertThat(result).hasSize(1);
        verify(patientRepository, times(1)).findAllByName("Петров");
    }

    @Test
    void updateFullInfo_shouldUpdateAndReturnPatient() {
        UpdatePatientFullInformationRequest request = new UpdatePatientFullInformationRequest();
        request.setFirstName("Анна");
        request.setLastName("Сидорова");
        request.setMiddleName("Ивановна");
        request.setDateOfBirth(LocalDate.of(1988, 3, 10));
        request.setGender("Женский");
        request.setPhoneNumber("+7 (926) 987-65-43");

        when(patientRepository.findById(1L)).thenReturn(Optional.of(testPatient));
        when(patientRepository.save(any(PatientEntity.class))).thenReturn(testPatient);

        UpdatePatientFullInformationResponse result = patientService.updateFullInfo(1L, request);

        assertThat(result.getFirstName()).isEqualTo("Анна");
        assertThat(result.getLastName()).isEqualTo("Сидорова");
        assertThat(result.getGender()).isEqualTo("Женский");
        verify(patientRepository, times(1)).save(testPatient);
    }

    @Test
    void existsById_shouldReturnTrue_whenPatientExists() {
        when(patientRepository.existsById(1L)).thenReturn(true);

        boolean result = patientService.existsById(1L);

        assertThat(result).isTrue();
    }

    @Test
    void updateFullInfo_shouldThrowResourceNotFoundException_whenPatientNotFound() {
        UpdatePatientFullInformationRequest request = new UpdatePatientFullInformationRequest();

        when(patientRepository.findById(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> patientService.updateFullInfo(999L, request))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Doctor with id 999 not found");
    }

    @Test
    void deleteById_shouldThrowResourceNotFoundException_whenPatientNotFound() {
        when(patientRepository.findById(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> patientService.deleteById(999L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Patient with id 999 not found");
    }

    @Test
    void deleteById_shouldCallUserRepositoryDelete_whenPatientExists() {
        when(patientRepository.findById(1L)).thenReturn(Optional.of(testPatient));
        when(userRepository.existsById(10L)).thenReturn(true);
        doNothing().when(userRepository).deleteById(10L);

        patientService.deleteById(1L);

        verify(userRepository, times(1)).deleteById(10L);
    }

    @Test
    void findAllShortInfo_shouldReturnEmptyList_whenNoPatients() {
        when(patientRepository.findAll()).thenReturn(List.of());

        List<PatientShortInfoResponse> result = patientService.findAllShortInfo();

        assertThat(result).isEmpty();
        verify(patientRepository, times(1)).findAll();
    }

    @Test
    void updateFullInfo_shouldSetMiddleNameToDash_whenNull() {
        UpdatePatientFullInformationRequest request = new UpdatePatientFullInformationRequest();
        request.setFirstName("Мария");
        request.setLastName("Иванова");
        request.setMiddleName(null);
        request.setDateOfBirth(LocalDate.of(1995, 7, 20));
        request.setGender("Женский");
        request.setPhoneNumber("+7 (926) 111-22-33");

        ArgumentCaptor<PatientEntity> patientCaptor = ArgumentCaptor.forClass(PatientEntity.class);

        when(patientRepository.findById(1L)).thenReturn(Optional.of(testPatient));
        when(patientRepository.save(patientCaptor.capture())).thenReturn(testPatient);

        patientService.updateFullInfo(1L, request);

        PatientEntity captured = patientCaptor.getValue();
        assertThat(captured.getMiddleName()).isEqualTo("-");
    }
}