package service.appointment;

import com.example.model.entity.*;
import com.example.model.enums.StatusEnum;
import com.example.repository.AppointmentRepository;
import com.example.repository.StatusRepository;
import com.example.service.AppointmentService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ParameterizedAppointmentServiceTest {

    @Mock
    private AppointmentRepository appointmentRepository;

    @Mock
    private StatusRepository statusRepository;

    @InjectMocks
    private AppointmentService appointmentService;

    private AppointmentEntity createTestAppointment(StatusEnum statusEnum) {
        StatusEntity status = new StatusEntity();
        status.setStatus(statusEnum);

        DoctorEntity doctor = new DoctorEntity();
        doctor.setId(10L);
        doctor.setFirstName("Алексей");
        doctor.setLastName("Иванов");
        doctor.setMiddleName("Сергеевич");
        doctor.setSpecializations(new HashSet<>());

        PatientEntity patient = new PatientEntity();
        patient.setId(1L);

        AppointmentEntity appointment = new AppointmentEntity();
        appointment.setId(100L);
        appointment.setDate(LocalDate.now());
        appointment.setTime(LocalTime.of(10, 0));
        appointment.setSymptoms("Тестовые симптомы");
        appointment.setStatus(status);
        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        return appointment;
    }

    @ParameterizedTest
    @EnumSource(StatusEnum.class)
    void findAllShortInfoByStatus_shouldWorkForAllStatuses(StatusEnum status) {
        AppointmentEntity appointment = createTestAppointment(status);
        when(appointmentRepository.findAllByStatusStatus(status)).thenReturn(List.of(appointment));

        var result = appointmentService.findAllShortInfoByStatus(status);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getStatus().getStatus()).isEqualTo(status);
    }

    @ParameterizedTest
    @ValueSource(longs = {1L, 5L, 10L, 100L})
    void findAllShortInfoByPatientId_shouldHandleDifferentPatientIds(long patientId) {
        AppointmentEntity appointment = createTestAppointment(StatusEnum.PENDING);
        when(appointmentRepository.findAllByPatientId(patientId)).thenReturn(List.of(appointment));

        var result = appointmentService.findAllShortInfoByPatientId(patientId);

        assertThat(result).hasSize(1);
    }

    @ParameterizedTest
    @CsvSource({
            "1, 10, Кашель",
            "2, 20, Головная боль",
            "3, 30, Температура"
    })
    void appointmentData_shouldBeMappedCorrectly(long patientId, long doctorId, String symptoms) {
        PatientEntity patient = new PatientEntity();
        patient.setId(patientId);
        DoctorEntity doctor = new DoctorEntity();
        doctor.setId(doctorId);
        doctor.setSpecializations(new HashSet<>());

        AppointmentEntity appointment = new AppointmentEntity();
        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        appointment.setSymptoms(symptoms);

        assertThat(appointment.getPatient().getId()).isEqualTo(patientId);
        assertThat(appointment.getDoctor().getId()).isEqualTo(doctorId);
        assertThat(appointment.getSymptoms()).isEqualTo(symptoms);
    }
}