package service;

import com.example.exception.ResourceNotFoundException;
import com.example.model.dto.doctor.request.UpdateDoctorFullInformationRequest;
import com.example.model.dto.doctor.response.DoctorFullInformationResponse;
import com.example.model.dto.doctor.response.DoctorShortInfoResponse;
import com.example.model.entity.DoctorEntity;
import com.example.model.entity.RoleEntity;
import com.example.model.entity.UserEntity;
import com.example.model.enums.RoleEnum;
import com.example.repository.DoctorRepository;
import com.example.repository.UserRepository;
import com.example.service.DoctorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DoctorServiceTest {

    @Mock
    private DoctorRepository doctorRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private DoctorService doctorService;

    private DoctorEntity testDoctor;
    private UserEntity testUser;

    @BeforeEach
    void setUp() {
        RoleEntity doctorRole = new RoleEntity(2L, RoleEnum.DOCTOR);

        testUser = new UserEntity();
        testUser.setId(5L);
        testUser.setUsername("dr_ivanov");
        testUser.setEmail("ivanov@clinic.com");
        testUser.setRole(doctorRole);

        testDoctor = new DoctorEntity();
        testDoctor.setId(1L);
        testDoctor.setFirstName("Алексей");
        testDoctor.setLastName("Иванов");
        testDoctor.setMiddleName("Сергеевич");
        testDoctor.setPhoneNumber("+7 (916) 111-22-33");
        testDoctor.setExperienceYears(12);
        testDoctor.setUser(testUser);
        testDoctor.setSpecializations(new HashSet<>());
    }

    @Test
    void findAllShortInfo_shouldReturnListOfDoctors() {
        when(doctorRepository.findAll()).thenReturn(List.of(testDoctor));

        List<DoctorShortInfoResponse> result = doctorService.findAllShortInfo();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getFirstName()).isEqualTo("Алексей");
        assertThat(result.get(0).getLastName()).isEqualTo("Иванов");
    }

    @Test
    void findAllShortInfoByName_shouldReturnDoctorsMatchingName() {
        when(doctorRepository.findAllByName("Иванов")).thenReturn(List.of(testDoctor));

        List<DoctorShortInfoResponse> result = doctorService.findAllShortInfoByName("Иванов");

        assertThat(result).hasSize(1);
        verify(doctorRepository, times(1)).findAllByName("Иванов");
    }

    @Test
    void findFullInfoById_shouldReturnDoctorFullInfo() {
        when(doctorRepository.findById(1L)).thenReturn(Optional.of(testDoctor));

        DoctorFullInformationResponse result = doctorService.findFullInfoById(1L);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getFirstName()).isEqualTo("Алексей");
        assertThat(result.getExperienceYears()).isEqualTo(12);
    }

    @Test
    void updateFullInfo_shouldUpdateAndReturnDoctor() {
        UpdateDoctorFullInformationRequest request = new UpdateDoctorFullInformationRequest();
        request.setFirstName("Петр");
        request.setLastName("Сидоров");
        request.setMiddleName("Алексеевич");
        request.setPhoneNumber("+7 (916) 999-88-77");

        when(doctorRepository.findById(1L)).thenReturn(Optional.of(testDoctor));
        when(doctorRepository.save(any(DoctorEntity.class))).thenReturn(testDoctor);

        DoctorFullInformationResponse result = doctorService.updateFullInfo(1L, request);

        assertThat(result.getFirstName()).isEqualTo("Петр");
        assertThat(result.getLastName()).isEqualTo("Сидоров");
        verify(doctorRepository, times(1)).save(testDoctor);
    }

    @Test
    void existsById_shouldReturnTrue_whenDoctorExists() {
        when(doctorRepository.existsById(1L)).thenReturn(true);

        boolean result = doctorService.existsById(1L);

        assertThat(result).isTrue();
    }

    @Test
    void findFullInfoById_shouldThrowResourceNotFoundException_whenDoctorNotFound() {
        when(doctorRepository.findById(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> doctorService.findFullInfoById(999L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Doctor with id 999 not found");
    }

    @Test
    void updateFullInfo_shouldThrowResourceNotFoundException_whenDoctorNotFound() {
        UpdateDoctorFullInformationRequest request = new UpdateDoctorFullInformationRequest();

        when(doctorRepository.findById(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> doctorService.updateFullInfo(999L, request))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void deleteById_shouldThrowResourceNotFoundException_whenDoctorNotFound() {
        when(doctorRepository.findById(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> doctorService.deleteById(999L))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void deleteById_shouldCallUserRepositoryDelete_whenDoctorExists() {
        // Arrange
        when(doctorRepository.findById(1L)).thenReturn(Optional.of(testDoctor));
        when(userRepository.existsById(5L)).thenReturn(true);
        doNothing().when(userRepository).deleteById(5L);

        doctorService.deleteById(1L);

        verify(userRepository, times(1)).deleteById(5L);
    }

    @Test
    void findAllShortInfo_shouldCallFindAllOnlyOnce() {
        when(doctorRepository.findAll()).thenReturn(List.of(testDoctor));

        doctorService.findAllShortInfo();

        verify(doctorRepository, times(1)).findAll();
    }

    @Test
    void updateFullInfo_shouldSetMiddleNameToDash_whenNull() {
        UpdateDoctorFullInformationRequest request = new UpdateDoctorFullInformationRequest();
        request.setFirstName("Иван");
        request.setLastName("Петров");
        request.setMiddleName(null);
        request.setPhoneNumber("+7 (916) 111-22-33");

        ArgumentCaptor<DoctorEntity> doctorCaptor = ArgumentCaptor.forClass(DoctorEntity.class);

        when(doctorRepository.findById(1L)).thenReturn(Optional.of(testDoctor));
        when(doctorRepository.save(doctorCaptor.capture())).thenReturn(testDoctor);

        doctorService.updateFullInfo(1L, request);

        DoctorEntity captured = doctorCaptor.getValue();
        assertThat(captured.getMiddleName()).isEqualTo("-");
    }

    @Test
    void findAllShortInfoBySpecialization_shouldReturnDoctors() {
        when(doctorRepository.findAllBySpecializationsName("Кардиолог")).thenReturn(List.of(testDoctor));

        List<DoctorShortInfoResponse> result = doctorService.findAllShortInfoBySpecialization("Кардиолог");

        assertThat(result).hasSize(1);
        verify(doctorRepository, times(1)).findAllBySpecializationsName("Кардиолог");
    }

    @Test
    void findAllShortInfoBySpecialization_shouldReturnEmptyList_whenNoDoctors() {
        when(doctorRepository.findAllBySpecializationsName("Nonexistent")).thenReturn(List.of());

        List<DoctorShortInfoResponse> result = doctorService.findAllShortInfoBySpecialization("Nonexistent");

        assertThat(result).isEmpty();
    }

    @Test
    void existsById_shouldReturnFalse_whenDoctorNotExists() {
        when(doctorRepository.existsById(999L)).thenReturn(false);

        boolean result = doctorService.existsById(999L);

        assertThat(result).isFalse();
    }

    @Test
    void updateFullInfo_shouldKeepExistingMiddleName_whenEmptyStringProvided() {
        UpdateDoctorFullInformationRequest request = new UpdateDoctorFullInformationRequest();
        request.setFirstName("Петр");
        request.setLastName("Сидоров");
        request.setMiddleName("");
        request.setPhoneNumber("+7 (916) 111-22-33");

        when(doctorRepository.findById(1L)).thenReturn(Optional.of(testDoctor));
        when(doctorRepository.save(any(DoctorEntity.class))).thenReturn(testDoctor);

        doctorService.updateFullInfo(1L, request);

        assertThat(testDoctor.getMiddleName()).isEqualTo("-");
    }
}