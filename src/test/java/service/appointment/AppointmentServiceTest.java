package service.appointment;

import com.example.exception.AccessDeniedException;
import com.example.exception.ResourceNotFoundException;
import com.example.model.dto.appointment.request.CreateAppointmentRequest;
import com.example.model.dto.appointment.request.UpdateAppointmentStatusRequest;
import com.example.model.dto.appointment.response.AppointmentFullInformationResponse;
import com.example.model.dto.appointment.response.AppointmentShortInformationResponse;
import com.example.model.dto.appointment.response.CreateAppointmentResponse;
import com.example.model.entity.*;
import com.example.model.enums.StatusEnum;
import com.example.repository.AppointmentRepository;
import com.example.repository.DoctorRepository;
import com.example.repository.PatientRepository;
import com.example.repository.StatusRepository;
import com.example.service.AppointmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AppointmentServiceTest {

    @Mock
    private AppointmentRepository appointmentRepository;

    @Mock
    private StatusRepository statusRepository;

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private DoctorRepository doctorRepository;

    @InjectMocks
    private AppointmentService appointmentService;

    private PatientEntity testPatient;
    private DoctorEntity testDoctor;
    private StatusEntity pendingStatus;
    private StatusEntity completedStatus;
    private AppointmentEntity testAppointment;

    @BeforeEach
    void setUp() {
        UserEntity patientUser = new UserEntity();
        patientUser.setId(1L);
        patientUser.setUsername("patient");

        testPatient = new PatientEntity();
        testPatient.setId(1L);
        testPatient.setFirstName("Иван");
        testPatient.setLastName("Петров");
        testPatient.setUser(patientUser);

        UserEntity doctorUser = new UserEntity();
        doctorUser.setId(2L);
        doctorUser.setUsername("doctor");

        testDoctor = new DoctorEntity();
        testDoctor.setId(10L);
        testDoctor.setFirstName("Алексей");
        testDoctor.setLastName("Иванов");
        testDoctor.setUser(doctorUser);
        testDoctor.setSpecializations(new HashSet<>());

        pendingStatus = new StatusEntity(1L, StatusEnum.PENDING);
        completedStatus = new StatusEntity(2L, StatusEnum.COMPLETED);

        testAppointment = new AppointmentEntity();
        testAppointment.setId(100L);
        testAppointment.setDate(LocalDate.now().plusDays(1));
        testAppointment.setTime(LocalTime.of(10, 0));
        testAppointment.setSymptoms("Кашель, температура");
        testAppointment.setStatus(pendingStatus);
        testAppointment.setPatient(testPatient);
        testAppointment.setDoctor(testDoctor);
    }

    @Test
    void createAppointment_shouldSaveAndReturnResponse() {
        CreateAppointmentRequest request = new CreateAppointmentRequest();
        request.setDate(LocalDate.now().plusDays(1));
        request.setTime(LocalTime.of(11, 0));
        request.setSymptoms("Головная боль");
        request.setPatientId(1L);
        request.setDoctorId(10L);

        when(statusRepository.findByStatus(StatusEnum.PENDING)).thenReturn(pendingStatus);
        when(patientRepository.findById(1L)).thenReturn(Optional.of(testPatient));
        when(doctorRepository.findById(10L)).thenReturn(Optional.of(testDoctor));
        when(appointmentRepository.save(any(AppointmentEntity.class))).thenReturn(testAppointment);

        CreateAppointmentResponse result = appointmentService.createAppointment(request);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(100L);
        verify(appointmentRepository, times(1)).save(any(AppointmentEntity.class));
    }

    @Test
    void findAllShortInfo_shouldReturnListOfShortInfoResponses() {
        when(appointmentRepository.findAll()).thenReturn(List.of(testAppointment));

        List<AppointmentShortInformationResponse> result = appointmentService.findAllShortInfo();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getSymptoms()).isEqualTo("Кашель, температура");
        verify(appointmentRepository, times(1)).findAll();
    }

    @Test
    void findFullInfoById_shouldReturnFullInfoResponse() {
        when(appointmentRepository.findById(100L)).thenReturn(Optional.of(testAppointment));

        AppointmentFullInformationResponse result = appointmentService.findFullInfoById(100L);

        assertThat(result).isNotNull();
        assertThat(result.getSymptoms()).isEqualTo("Кашель, температура");
    }

    @Test
    void updateStatus_shouldChangeAppointmentStatus() {
        when(appointmentRepository.findById(100L)).thenReturn(Optional.of(testAppointment));
        when(statusRepository.findByStatus(StatusEnum.COMPLETED)).thenReturn(completedStatus);
        when(appointmentRepository.save(any(AppointmentEntity.class))).thenReturn(testAppointment);

        AppointmentFullInformationResponse result = appointmentService.updateStatus(100L, StatusEnum.COMPLETED);

        assertThat(result.getStatus().getStatus()).isEqualTo(StatusEnum.COMPLETED);
        verify(appointmentRepository, times(1)).save(testAppointment);
    }

    @Test
    void updateDoctorAppointmentStatus_shouldUpdateWhenDoctorMatches() {
        UpdateAppointmentStatusRequest request = new UpdateAppointmentStatusRequest();
        request.setDoctorId(10);
        request.setStatus(StatusEnum.COMPLETED);

        when(appointmentRepository.findById(100L)).thenReturn(Optional.of(testAppointment));
        when(statusRepository.findByStatus(StatusEnum.COMPLETED)).thenReturn(completedStatus);
        when(appointmentRepository.save(any(AppointmentEntity.class))).thenReturn(testAppointment);

        AppointmentFullInformationResponse result = appointmentService.updateDoctorAppointmentStatus(100L, request);

        assertThat(result).isNotNull();
        verify(appointmentRepository, times(1)).save(testAppointment);
    }

    @Test
    void updateSymptoms_shouldChangeAppointmentSymptoms() {
        when(appointmentRepository.findById(100L)).thenReturn(Optional.of(testAppointment));
        when(appointmentRepository.save(any(AppointmentEntity.class))).thenReturn(testAppointment);

        AppointmentFullInformationResponse result = appointmentService.updateSymptoms(100L, "Новые симптомы");

        assertThat(result.getSymptoms()).isEqualTo("Новые симптомы");
    }

    @Test
    void deleteById_shouldDeleteWhenExists() {
        when(appointmentRepository.existsById(100L)).thenReturn(true);
        doNothing().when(appointmentRepository).deleteById(100L);

        appointmentService.deleteById(100L);

        verify(appointmentRepository, times(1)).deleteById(100L);
    }

    @Test
    void createAppointment_shouldThrowResourceNotFoundException_whenPatientNotFound() {
        CreateAppointmentRequest request = new CreateAppointmentRequest();
        request.setPatientId(999L);
        request.setDoctorId(10L);

        when(statusRepository.findByStatus(StatusEnum.PENDING)).thenReturn(pendingStatus);
        when(patientRepository.findById(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> appointmentService.createAppointment(request))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Patient with id 999 not found");
    }

    @Test
    void findFullInfoById_shouldThrowResourceNotFoundException_whenNotFound() {
        when(appointmentRepository.findById(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> appointmentService.findFullInfoById(999L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Appointment with id 999 not found");
    }

    @Test
    void updateDoctorAppointmentStatus_shouldThrowAccessDeniedException_whenDoctorMismatch() {
        UpdateAppointmentStatusRequest request = new UpdateAppointmentStatusRequest();
        request.setDoctorId(999);
        request.setStatus(StatusEnum.COMPLETED);

        when(appointmentRepository.findById(100L)).thenReturn(Optional.of(testAppointment));

        assertThatThrownBy(() -> appointmentService.updateDoctorAppointmentStatus(100L, request))
                .isInstanceOf(AccessDeniedException.class);
    }

    @Test
    void createAppointment_shouldCallRepositorySaveOnlyOnce() {
        CreateAppointmentRequest request = new CreateAppointmentRequest();
        request.setPatientId(1L);
        request.setDoctorId(10L);

        when(statusRepository.findByStatus(StatusEnum.PENDING)).thenReturn(pendingStatus);
        when(patientRepository.findById(1L)).thenReturn(Optional.of(testPatient));
        when(doctorRepository.findById(10L)).thenReturn(Optional.of(testDoctor));
        when(appointmentRepository.save(any(AppointmentEntity.class))).thenReturn(testAppointment);

        appointmentService.createAppointment(request);

        verify(appointmentRepository, times(1)).save(any(AppointmentEntity.class));
    }

    @Test
    void updateStatus_shouldNotCallSave_whenAppointmentNotFound() {
        when(appointmentRepository.findById(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> appointmentService.updateStatus(999L, StatusEnum.COMPLETED))
                .isInstanceOf(ResourceNotFoundException.class);

        verify(appointmentRepository, never()).save(any(AppointmentEntity.class));
    }

    @Test
    void createAppointment_shouldPassCorrectDataToRepository() {
        CreateAppointmentRequest request = new CreateAppointmentRequest();
        request.setDate(LocalDate.of(2026, 6, 15));
        request.setTime(LocalTime.of(14, 30));
        request.setSymptoms("Боль в животе");
        request.setPatientId(1L);
        request.setDoctorId(10L);

        ArgumentCaptor<AppointmentEntity> appointmentCaptor = ArgumentCaptor.forClass(AppointmentEntity.class);

        when(statusRepository.findByStatus(StatusEnum.PENDING)).thenReturn(pendingStatus);
        when(patientRepository.findById(1L)).thenReturn(Optional.of(testPatient));
        when(doctorRepository.findById(10L)).thenReturn(Optional.of(testDoctor));
        when(appointmentRepository.save(appointmentCaptor.capture())).thenReturn(testAppointment);

        appointmentService.createAppointment(request);

        AppointmentEntity captured = appointmentCaptor.getValue();
        assertThat(captured.getDate()).isEqualTo(LocalDate.of(2026, 6, 15));
        assertThat(captured.getTime()).isEqualTo(LocalTime.of(14, 30));
        assertThat(captured.getSymptoms()).isEqualTo("Боль в животе");
        assertThat(captured.getPatient()).isEqualTo(testPatient);
        assertThat(captured.getDoctor()).isEqualTo(testDoctor);
        assertThat(captured.getStatus()).isEqualTo(pendingStatus);
    }

    @Test
    void findAllShortInfoByPatientId_shouldReturnEmptyList_whenNoAppointments() {
        when(appointmentRepository.findAllByPatientId(999L)).thenReturn(List.of());

        List<AppointmentShortInformationResponse> result = appointmentService.findAllShortInfoByPatientId(999L);

        assertThat(result).isEmpty();
        verify(appointmentRepository, times(1)).findAllByPatientId(999L);
    }

    @Test
    void findAllShortInfoByDoctorId_shouldReturnAppointments() {
        when(appointmentRepository.findAllByDoctorId(10L)).thenReturn(List.of(testAppointment));

        List<AppointmentShortInformationResponse> result = appointmentService.findAllShortInfoByDoctorId(10L);

        assertThat(result).hasSize(1);
        verify(appointmentRepository, times(1)).findAllByDoctorId(10L);
    }

    @Test
    void findAllShortInfoByDoctorId_shouldReturnEmptyList_whenNoAppointments() {
        when(appointmentRepository.findAllByDoctorId(999L)).thenReturn(List.of());

        List<AppointmentShortInformationResponse> result = appointmentService.findAllShortInfoByDoctorId(999L);

        assertThat(result).isEmpty();
    }

    @Test
    void findAllShortInfoByDoctorName_shouldReturnAppointments() {
        when(appointmentRepository.findAllByDoctorName("Иванов")).thenReturn(List.of(testAppointment));

        List<AppointmentShortInformationResponse> result = appointmentService.findAllShortInfoByDoctorName("Иванов");

        assertThat(result).hasSize(1);
        verify(appointmentRepository, times(1)).findAllByDoctorName("Иванов");
    }

    @Test
    void findAllShortInfoByDoctorName_shouldReturnEmptyList_whenNoMatch() {
        when(appointmentRepository.findAllByDoctorName("Nonexistent")).thenReturn(List.of());

        List<AppointmentShortInformationResponse> result = appointmentService.findAllShortInfoByDoctorName("Nonexistent");

        assertThat(result).isEmpty();
    }

    @Test
    void findAllShortInfoByPatientIdAndPartDoctorName_shouldReturnAppointments() {
        when(appointmentRepository.findAllByPatientIdAndPartDoctorName(1L, "Иван")).thenReturn(List.of(testAppointment));

        List<AppointmentShortInformationResponse> result = appointmentService.findAllShortInfoByPatientIdAndPartDoctorName(1L, "Иван");

        assertThat(result).hasSize(1);
        verify(appointmentRepository, times(1)).findAllByPatientIdAndPartDoctorName(1L, "Иван");
    }

    @Test
    void findAllShortInfoByPatientIdAndStatus_shouldReturnAppointments() {
        when(appointmentRepository.findAllByPatientIdAndStatusStatus(1L, StatusEnum.PENDING)).thenReturn(List.of(testAppointment));

        List<AppointmentShortInformationResponse> result = appointmentService.findAllShortInfoByPatientIdAndStatus(1L, StatusEnum.PENDING);

        assertThat(result).hasSize(1);
        verify(appointmentRepository, times(1)).findAllByPatientIdAndStatusStatus(1L, StatusEnum.PENDING);
    }

    @Test
    void updateStatus_shouldThrowResourceNotFoundException_whenStatusNotFound() {
        when(appointmentRepository.findById(100L)).thenReturn(Optional.of(testAppointment));
        when(statusRepository.findByStatus(StatusEnum.COMPLETED)).thenReturn(null);

        assertThatThrownBy(() -> appointmentService.updateStatus(100L, StatusEnum.COMPLETED))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Status not found with name: 'COMPLETED'");
    }

    @Test
    void updateDoctorAppointmentStatus_shouldThrowResourceNotFoundException_whenStatusNotFound() {
        UpdateAppointmentStatusRequest request = new UpdateAppointmentStatusRequest();
        request.setDoctorId(10);
        request.setStatus(StatusEnum.COMPLETED);

        when(appointmentRepository.findById(100L)).thenReturn(Optional.of(testAppointment));
        when(statusRepository.findByStatus(StatusEnum.COMPLETED)).thenReturn(null);

        assertThatThrownBy(() -> appointmentService.updateDoctorAppointmentStatus(100L, request))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void deleteById_shouldThrowResourceNotFoundException_whenNotExists() {
        when(appointmentRepository.existsById(999L)).thenReturn(false);

        assertThatThrownBy(() -> appointmentService.deleteById(999L))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void existsById_shouldReturnFalse_whenNotExists() {
        when(appointmentRepository.existsById(999L)).thenReturn(false);

        boolean result = appointmentService.existsById(999L);

        assertThat(result).isFalse();
    }

    @Test
    void existsById_shouldReturnTrue_whenExists() {
        when(appointmentRepository.existsById(100L)).thenReturn(true);

        boolean result = appointmentService.existsById(100L);

        assertThat(result).isTrue();
    }
}