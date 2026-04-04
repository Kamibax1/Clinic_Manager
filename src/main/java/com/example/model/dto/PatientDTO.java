package com.example.model.dto;

import com.example.model.entity.PatientEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

public class PatientDTO {
    @Id
    @Getter @Setter
    @JsonProperty("id_patient")
    private Long id;

    @Getter @Setter
    @JsonProperty("full_name")
    private String fullName;

    @Getter @Setter
    @JsonProperty("date_of_birth")
    private LocalDate dateOfBirth;

    @Getter @Setter
    private String gender;

    @Getter @Setter
    @JsonProperty("phone_number")
    private String phoneNumber;

    @Getter @Setter
    private String email;

    public static PatientDTO fromEntity(PatientEntity patientEntity) {
        PatientDTO patientDTO = new PatientDTO();
        patientDTO.setId(patientEntity.getId());
        patientDTO.fullName = patientEntity.getFullName();
        patientDTO.dateOfBirth = patientEntity.getDateOfBirth();
        patientDTO.gender = patientEntity.getGender();
        patientDTO.phoneNumber = patientEntity.getPhoneNumber();
        patientDTO.email = patientEntity.getUser().getEmail();

        return patientDTO;
    }

    public static PatientEntity updateMap(PatientEntity patientEntity, PatientDTO patientDTO) {
        patientEntity.setFullName(patientDTO.fullName);
        patientEntity.setDateOfBirth(patientDTO.dateOfBirth);
        patientEntity.setPhoneNumber(patientDTO.phoneNumber);
        return patientEntity;
    }
}
