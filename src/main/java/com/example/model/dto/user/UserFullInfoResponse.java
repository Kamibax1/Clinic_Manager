package com.example.model.dto.user;

import com.example.model.dto.doctor.response.DoctorFullInformationResponse;
import com.example.model.dto.patient.response.PatientFullInfoForAdminResponse;
import com.example.model.enums.RoleEnum;
import lombok.Getter;
import lombok.Setter;

public class UserFullInfoResponse {
    @Getter @Setter
    private Long id;

    @Getter @Setter
    private String username;

    @Getter @Setter
    private String email;

    @Getter @Setter
    private RoleEnum role;

    @Getter @Setter
    private boolean enabled;

    @Getter @Setter
    private String profileType;

    @Getter @Setter
    private DoctorFullInformationResponse doctorInfo;

    @Getter @Setter
    private PatientFullInfoForAdminResponse patientInfo;

    public UserFullInfoResponse() {
    }

    public UserFullInfoResponse(Long id, String username, String email, RoleEnum role, boolean enabled, String profileType, DoctorFullInformationResponse doctorInfo, PatientFullInfoForAdminResponse patientInfo) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.role = role;
        this.enabled = enabled;
        this.profileType = profileType;
        this.doctorInfo = doctorInfo;
        this.patientInfo = patientInfo;
    }
}
