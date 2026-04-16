package com.example.model.dto;

import com.example.model.entity.DoctorEntity;
import com.example.model.entity.SpecializationEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;
import java.util.stream.Collectors;

public class DoctorDTO {
    @Id
    @Getter @Setter
    @JsonProperty("id_doctor")
    private Long id;

    @JsonProperty("first_name")
    @Getter @Setter
    private String firstName;

    @JsonProperty("last_name")
    @Getter @Setter
    private String lastName;

    @JsonProperty("experience_years")
    @Getter @Setter
    private int experienceYears;

    @Getter @Setter
    @JsonProperty("specializations")
    private Set<SpecializationDTO> specializations;

    public static DoctorDTO fromEntity(DoctorEntity doctorEntity) {
        DoctorDTO doctorDTO = new DoctorDTO();
        doctorDTO.id = doctorEntity.getId();
        doctorDTO.firstName = doctorEntity.getFirstName();
        doctorDTO.lastName = doctorEntity.getLastName();
        doctorDTO.experienceYears = doctorEntity.getExperienceYears();

        doctorDTO.specializations = doctorEntity.getSpecializations().stream()
                .map(SpecializationDTO::fromEntity)
                .collect(Collectors.toSet());
        return doctorDTO;
    }

    public static DoctorEntity toEntity(DoctorDTO dto) {
        DoctorEntity entity = new DoctorEntity();
        entity.setId(dto.id);
        entity.setFirstName(dto.firstName);
        entity.setLastName(dto.lastName);
        entity.setExperienceYears(dto.experienceYears);
        entity.setSpecializations(dto.specializations.stream()
                .map(SpecializationDTO::toEntity)
                .collect(Collectors.toSet())
        );
        return entity;
    }
}
