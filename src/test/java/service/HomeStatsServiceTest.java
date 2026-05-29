package service;

import com.example.model.dto.stats.HomeStatsResponse;
import com.example.model.entity.PatientEntity;
import com.example.model.entity.DoctorEntity;
import com.example.model.enums.StatusEnum;
import com.example.repository.AppointmentRepository;
import com.example.repository.DoctorRepository;
import com.example.repository.PatientRepository;
import com.example.service.HomeStatsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HomeStatsServiceTest {

    @Mock
    private AppointmentRepository appointmentRepository;

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private DoctorRepository doctorRepository;

    @InjectMocks
    private HomeStatsService homeStatsService;

    @Test
    void getHomeStats_shouldReturnCorrectStats() {
        when(appointmentRepository.countByDate(LocalDate.now())).thenReturn(5);
        when(patientRepository.findAll()).thenReturn(List.of(new PatientEntity(), new PatientEntity()));
        when(doctorRepository.findAll()).thenReturn(List.of(new DoctorEntity()));
        when(appointmentRepository.countByStatusStatus(StatusEnum.COMPLETED)).thenReturn(10);

        HomeStatsResponse result = homeStatsService.getHomeStats();

        assertThat(result.getCountAppointmentsToday()).isEqualTo(5);
        assertThat(result.getCountPatients()).isEqualTo(2);
        assertThat(result.getCountDoctors()).isEqualTo(1);
        assertThat(result.getCountAppointmentsCompleted()).isEqualTo(10);
    }
}