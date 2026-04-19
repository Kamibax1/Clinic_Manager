package com.example.model.dto;

import com.example.model.entity.DoctorEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;
import java.util.stream.Collectors;

public class DoctorShortInfoResponse {
    @Id
    @Getter @Setter
    @JsonProperty("id_doctor_short_information")
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

    @JsonProperty("experience_years")
    @Getter @Setter
    private int experienceYears;

    @Getter @Setter
    private Set<SpecializationDTO> specializations;

    public DoctorShortInfoResponse() {
    }

    public DoctorShortInfoResponse(String firstName, String lastName, String middleName, int experienceYears, Set<SpecializationDTO> specializations) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.experienceYears = experienceYears;
        this.specializations = specializations;
    }

    public static DoctorShortInfoResponse fromEntity(DoctorEntity entity) {
        DoctorShortInfoResponse dto = new DoctorShortInfoResponse();
        dto.setId(entity.getId());
        dto.firstName = entity.getFirstName();
        dto.lastName = entity.getLastName();
        dto.middleName = entity.getMiddleName();
        dto.experienceYears = entity.getExperienceYears();
        dto.specializations = entity.getSpecializations().stream()
                .map(SpecializationDTO::fromEntity)
                .collect(Collectors.toSet());
        return dto;
    }

    public static DoctorEntity toEntity(DoctorShortInfoResponse dto) {
        DoctorEntity entity = new DoctorEntity();
        entity.setId(dto.id);
        entity.setFirstName(dto.firstName);
        entity.setLastName(dto.lastName);
        entity.setMiddleName(dto.middleName);
        entity.setExperienceYears(dto.experienceYears);
        entity.setSpecializations(dto.specializations.stream()
                .map(SpecializationDTO::toEntity)
                .collect(Collectors.toSet())
        );
        return entity;
    }
}
