package com.example.model.dto.patient.response;

import com.example.model.entity.PatientEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

public class PatientShortInfoResponse {
    @Id
    @Getter @Setter
    @JsonProperty("id_patient_short_information")
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

    @JsonProperty("phone_number")
    @Getter @Setter
    private String phoneNumber;

    public PatientShortInfoResponse() {
    }

    public PatientShortInfoResponse(String firstName, String lastName, String middleName, LocalDate dateOfBirth, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.dateOfBirth = dateOfBirth;
        this.phoneNumber = phoneNumber;
    }

    public static PatientShortInfoResponse fromEntity(PatientEntity entity) {
        PatientShortInfoResponse dto = new PatientShortInfoResponse();
        dto.id = entity.getId();
        dto.firstName = entity.getFirstName();
        dto.lastName = entity.getLastName();
        dto.middleName = entity.getMiddleName();
        dto.dateOfBirth = entity.getDateOfBirth();
        dto.phoneNumber = entity.getPhoneNumber();
        return dto;
    }

    public static PatientEntity toEntity(PatientShortInfoResponse dto) {
        PatientEntity entity = new PatientEntity();
        entity.setId(dto.id);
        entity.setFirstName(dto.firstName);
        entity.setLastName(dto.lastName);
        entity.setMiddleName(dto.middleName);
        entity.setDateOfBirth(dto.dateOfBirth);
        entity.setPhoneNumber(dto.phoneNumber);
        return entity;
    }
}
