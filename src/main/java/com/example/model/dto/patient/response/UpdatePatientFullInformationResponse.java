package com.example.model.dto.patient.response;

import com.example.model.entity.PatientEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

public class UpdatePatientFullInformationResponse {
    @Getter @Setter
    @JsonProperty("update_patient_full_information")
    private Long id;

    @Getter @Setter
    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    @Getter @Setter
    private String lastName;

    @JsonProperty("middle_name")
    @Getter @Setter
    private String middleName;

    @JsonProperty("date_of_birth")
    @Getter @Setter
    private LocalDate dateOfBirth;

    @Getter @Setter
    private String gender;

    @JsonProperty("phone_number")
    @Getter @Setter
    private String phoneNumber;

    public UpdatePatientFullInformationResponse() {
    }

    public UpdatePatientFullInformationResponse(Long id, String firstName, String lastName, String middleName, LocalDate dateOfBirth, String gender, String phoneNumber) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
    }

    public static UpdatePatientFullInformationResponse fromEntity(PatientEntity entity) {
        UpdatePatientFullInformationResponse response = new UpdatePatientFullInformationResponse();
        response.setId(entity.getId());
        response.setFirstName(entity.getFirstName());
        response.setLastName(entity.getLastName());
        response.setMiddleName(entity.getMiddleName());
        response.setDateOfBirth(entity.getDateOfBirth());
        response.setGender(entity.getGender());
        response.setPhoneNumber(entity.getPhoneNumber());
        return response;
    }
}
