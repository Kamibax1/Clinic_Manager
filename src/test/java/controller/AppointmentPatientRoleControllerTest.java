package controller;

import com.example.ClinicManagementApplication;
import com.example.model.dto.StatusDTO;
import com.example.model.dto.appointment.request.CreateAppointmentRequest;
import com.example.model.dto.appointment.response.CreateAppointmentResponse;
import com.example.model.dto.doctor.response.DoctorShortInfoResponse;
import com.example.model.dto.patient.response.PatientShortInfoResponse;
import com.example.model.enums.StatusEnum;
import com.example.service.AppointmentService;
import com.example.service.DoctorService;
import com.example.service.PatientService;
import com.example.service.StatusService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = ClinicManagementApplication.class)
@AutoConfigureMockMvc
class AppointmentPatientRoleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private AppointmentService appointmentService;

    @MockitoBean
    private DoctorService doctorService;

    @MockitoBean
    private PatientService patientService;

    @MockitoBean
    private StatusService statusService;

    private CreateAppointmentRequest validRequest;
    private CreateAppointmentResponse createdResponse;

    @BeforeEach
    void setUp() {
        validRequest = new CreateAppointmentRequest();
        validRequest.setDate(LocalDate.now().plusDays(1));
        validRequest.setTime(LocalTime.of(10, 30));
        validRequest.setSymptoms("Кашель и температура");
        validRequest.setPatientId(1L);
        validRequest.setDoctorId(10L);

        PatientShortInfoResponse patientInfo = new PatientShortInfoResponse();
        patientInfo.setId(1L);
        patientInfo.setFirstName("Иван");
        patientInfo.setLastName("Петров");

        DoctorShortInfoResponse doctorInfo = new DoctorShortInfoResponse();
        doctorInfo.setId(10L);
        doctorInfo.setFirstName("Алексей");
        doctorInfo.setLastName("Иванов");
        doctorInfo.setSpecializations(new HashSet<>());

        StatusDTO statusDTO = new StatusDTO(1L, StatusEnum.PENDING);

        createdResponse = new CreateAppointmentResponse();
        createdResponse.setId(100L);
        createdResponse.setDate(validRequest.getDate());
        createdResponse.setTime(validRequest.getTime());
        createdResponse.setSymptoms(validRequest.getSymptoms());
        createdResponse.setStatus(statusDTO);
        createdResponse.setPatient(patientInfo);
        createdResponse.setDoctor(doctorInfo);
    }

    @Test
    @WithMockUser(roles = "PATIENT")
    void createAppointment_shouldReturn201Created_whenValidRequest() throws Exception {
        when(appointmentService.createAppointment(any(CreateAppointmentRequest.class))).thenReturn(createdResponse);

        mockMvc.perform(post("/api/patient/appointments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validRequest)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.id_create_appointment").value(100))
                .andExpect(jsonPath("$.symptoms").value("Кашель и температура"))
                .andExpect(jsonPath("$.status.status").value("PENDING"));
    }

    @Test
    @WithMockUser(roles = "PATIENT")
    void createAppointment_shouldReturn400_whenDateIsPast() throws Exception {
        CreateAppointmentRequest invalidRequest = new CreateAppointmentRequest();
        invalidRequest.setDate(LocalDate.now().minusDays(1));
        invalidRequest.setTime(LocalTime.now());
        invalidRequest.setSymptoms("Симптомы");
        invalidRequest.setPatientId(1L);
        invalidRequest.setDoctorId(10L);

        mockMvc.perform(post("/api/patient/appointments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = "PATIENT")
    void getAllDoctors_shouldReturnListWithCorrectFields() throws Exception {
        DoctorShortInfoResponse doctor = new DoctorShortInfoResponse();
        doctor.setId(1L);
        doctor.setFirstName("Алексей");
        doctor.setLastName("Иванов");
        doctor.setSpecializations(new HashSet<>());

        when(doctorService.findAllShortInfo()).thenReturn(List.of(doctor));

        mockMvc.perform(get("/api/patient/doctors/information/short"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id_doctor_short_information").value(1))
                .andExpect(jsonPath("$[0].first_name").value("Алексей"))
                .andExpect(jsonPath("$[0].last_name").value("Иванов"));
    }
}