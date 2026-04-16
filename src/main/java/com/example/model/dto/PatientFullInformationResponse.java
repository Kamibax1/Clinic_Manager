package com.example.model.dto;

import com.example.model.entity.PatientEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

public class PatientFullInformationResponse {
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

    @Getter @Setter
    private String email;

    public PatientFullInformationResponse() {
    }

    public PatientFullInformationResponse(String firstName, String lastName, String middleName, LocalDate dateOfBirth, String gender, String phoneNumber, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public static PatientFullInformationResponse fromEntity(PatientEntity entity) {
        PatientFullInformationResponse dto = new PatientFullInformationResponse();
        dto.firstName = entity.getFirstName();
        dto.lastName = entity.getLastName();
        dto.middleName = entity.getMiddleName();
        dto.dateOfBirth = entity.getDateOfBirth();
        dto.gender = entity.getGender();
        dto.phoneNumber = entity.getPhoneNumber();
        dto.email = entity.getUser().getEmail();
        return dto;
    }
}
