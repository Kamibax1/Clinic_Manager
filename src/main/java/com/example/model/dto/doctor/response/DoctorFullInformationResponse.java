package com.example.model.dto.doctor.response;

import com.example.model.dto.SpecializationDTO;
import com.example.model.entity.DoctorEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;
import java.util.stream.Collectors;

public class DoctorFullInformationResponse {
    @Getter @Setter
    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    @Getter @Setter
    private String lastName;

    @JsonProperty("middle_name")
    @Getter @Setter
    private String middleName;

    @JsonProperty("experience_years")
    @Getter @Setter
    private int experienceYears;

    @JsonProperty("phone_number")
    @Getter @Setter
    private String phoneNumber;

    @Getter @Setter
    private Set<SpecializationDTO> specializations;

    public DoctorFullInformationResponse() {
    }

    public DoctorFullInformationResponse(String firstName, String lastName, String middleName, int experienceYears, String phoneNumber, Set<SpecializationDTO> specializations) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.experienceYears = experienceYears;
        this.phoneNumber = phoneNumber;
        this.specializations = specializations;
    }

    public static DoctorFullInformationResponse fromEntity(DoctorEntity entity) {
        DoctorFullInformationResponse dto = new DoctorFullInformationResponse();
        dto.firstName = entity.getFirstName();
        dto.lastName = entity.getLastName();
        dto.middleName = entity.getMiddleName();
        dto.experienceYears = entity.getExperienceYears();
        dto.phoneNumber = entity.getPhoneNumber();
        dto.specializations = entity.getSpecializations().stream()
                .map(SpecializationDTO::fromEntity)
                .collect(Collectors.toSet());
        return dto;
    }
}
