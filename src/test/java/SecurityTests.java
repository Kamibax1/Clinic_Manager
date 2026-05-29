import com.example.ClinicManagementApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = ClinicManagementApplication.class)
@AutoConfigureMockMvc
class SecurityTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(roles = "PATIENT")
    void patientShouldAccessPatientEndpoints() throws Exception {
        mockMvc.perform(get("/api/patient/doctors/information/short"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "DOCTOR")
    void doctorShouldAccessDoctorEndpoints() throws Exception {
        mockMvc.perform(get("/api/doctor/appointments/information/short"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "PATIENT")
    void patientShouldNotAccessAdminEndpoints() throws Exception {
        mockMvc.perform(get("/api/admin/users"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "PATIENT")
    void patientShouldNotAccessDoctorEndpoints() throws Exception {
        mockMvc.perform(get("/api/doctor/appointments/information/short"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "DOCTOR")
    void doctorShouldNotAccessAdminEndpoints() throws Exception {
        mockMvc.perform(get("/api/admin/users"))
                .andExpect(status().isForbidden());
    }

    @Test
    void unauthenticatedUserShouldNotAccessPatientEndpoints() throws Exception {
        mockMvc.perform(get("/api/patient/doctors/information/short"))
                .andExpect(status().isForbidden());
    }

    @Test
    void unauthenticatedUserShouldNotAccessDoctorEndpoints() throws Exception {
        mockMvc.perform(get("/api/doctor/appointments/information/short"))
                .andExpect(status().isForbidden());
    }

    @Test
    void unauthenticatedUserShouldNotAccessAdminEndpoints() throws Exception {
        mockMvc.perform(get("/api/admin/users"))
                .andExpect(status().isForbidden());
    }
}