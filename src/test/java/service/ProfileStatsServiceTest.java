package service;

import com.example.model.dto.stats.ProfileStatsResponse;
import com.example.model.entity.DoctorEntity;
import com.example.model.entity.PatientEntity;
import com.example.model.entity.UserEntity;
import com.example.model.enums.StatusEnum;
import com.example.repository.AppointmentRepository;
import com.example.repository.DoctorRepository;
import com.example.repository.PatientRepository;
import com.example.service.ProfileStatsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProfileStatsServiceTest {

    @Mock
    private AppointmentRepository appointmentRepository;

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private DoctorRepository doctorRepository;

    @InjectMocks
    private ProfileStatsService profileStatsService;

    private PatientEntity testPatient;
    private DoctorEntity testDoctor;
    private UserEntity testUser;

    @BeforeEach
    void setUp() {
        testUser = new UserEntity();
        testUser.setId(1L);
        testUser.setCreatedAt(new Timestamp(System.currentTimeMillis()));

        testPatient = new PatientEntity();
        testPatient.setId(1L);
        testPatient.setUser(testUser);

        testDoctor = new DoctorEntity();
        testDoctor.setId(10L);
        testDoctor.setUser(testUser);
    }

    @Test
    void getProfileStatsPatient_shouldReturnCorrectStats() {
        when(patientRepository.findById(1L)).thenReturn(Optional.of(testPatient));
        when(appointmentRepository.countByPatientId(1L)).thenReturn(15);
        when(appointmentRepository.countByPatientIdAndStatusStatusIn(1L,
                List.of(StatusEnum.PENDING, StatusEnum.IN_PROGRESS, StatusEnum.SCHEDULED)))
                .thenReturn(3);

        ProfileStatsResponse result = profileStatsService.getProfileStatsPatient(1L);

        assertThat(result.getCountAppointment()).isEqualTo(15);
        assertThat(result.getCountCurrentAppointment()).isEqualTo(3);
        assertThat(result.getDateOfRegistration()).isEqualTo(LocalDate.now());
    }

    @Test
    void getProfileStatsDoctor_shouldReturnCorrectStats() {
        when(doctorRepository.findById(10L)).thenReturn(Optional.of(testDoctor));
        when(appointmentRepository.countByDoctorId(10L)).thenReturn(25);
        when(appointmentRepository.countByDoctorIdAndStatusStatusIn(10L,
                List.of(StatusEnum.PENDING, StatusEnum.IN_PROGRESS, StatusEnum.SCHEDULED)))
                .thenReturn(5);

        ProfileStatsResponse result = profileStatsService.getProfileStatsDoctor(10L);

        assertThat(result.getCountAppointment()).isEqualTo(25);
        assertThat(result.getCountCurrentAppointment()).isEqualTo(5);
        assertThat(result.getDateOfRegistration()).isEqualTo(LocalDate.now());
    }
}
