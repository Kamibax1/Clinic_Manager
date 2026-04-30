package com.example.model.dto.patient.response;

import com.example.model.entity.PatientEntity;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

public class PatientFullInfoForAdminResponse {
    @Getter @Setter
    private Long id;

    @Getter @Setter
    private String firstName;

    @Getter @Setter
    private String lastName;

    @Getter @Setter
    private String middleName;

    @Getter @Setter
    private LocalDate dateOfBirth;

    @Getter @Setter
    private String gender;

    @Getter @Setter
    private String phoneNumber;

    public PatientFullInfoForAdminResponse() {
    }

    public PatientFullInfoForAdminResponse(Long id, String firstName, String lastName, String middleName, LocalDate dateOfBirth, String gender, String phoneNumber) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
    }

    public static PatientFullInfoForAdminResponse fromEntity(PatientEntity entity) {
        PatientFullInfoForAdminResponse dto = new PatientFullInfoForAdminResponse();
        dto.firstName = entity.getFirstName();
        dto.lastName = entity.getLastName();
        dto.middleName = entity.getMiddleName();
        dto.dateOfBirth = entity.getDateOfBirth();
        dto.gender = entity.getGender();
        dto.phoneNumber = entity.getPhoneNumber();
        return dto;
    }
}
