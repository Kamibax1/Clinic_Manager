package com.example.model.dto.user.request;

import com.example.model.dto.doctor.request.UpdateDoctorFullInfoForAdminRequest;
import com.example.model.dto.patient.request.UpdatePatientFullInformationRequest;
import com.example.model.enums.RoleEnum;
import lombok.Getter;
import lombok.Setter;

public class UpdateUserFullInfoRequest {
    @Getter @Setter
    private String username;

    @Getter @Setter
    private String email;

    @Getter @Setter
    private String password;

    @Getter @Setter
    private Boolean enabled;

    @Getter @Setter
    private RoleEnum role;

    @Getter @Setter
    private UpdateDoctorFullInfoForAdminRequest doctorInfo;

    @Getter @Setter
    private UpdatePatientFullInformationRequest patientInfo;

    public UpdateUserFullInfoRequest() {
    }

    public UpdateUserFullInfoRequest(String username, String email, String password, Boolean enabled, RoleEnum role, UpdateDoctorFullInfoForAdminRequest doctorInfo, UpdatePatientFullInformationRequest patientInfo) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.enabled = enabled;
        this.role = role;
        this.doctorInfo = doctorInfo;
        this.patientInfo = patientInfo;
    }
}
