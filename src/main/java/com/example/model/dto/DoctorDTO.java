package com.example.model.dto;

import com.example.model.entity.DoctorEntity;
import com.example.model.entity.SpecializationEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

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

    @Getter @Setter
    private List<String> specialization;

    public static DoctorDTO fromEntity(DoctorEntity doctorEntity) {
        DoctorDTO doctorDTO = new DoctorDTO();
        doctorDTO.id = doctorEntity.getId();
        doctorDTO.firstName = doctorEntity.getFirstName();
        doctorDTO.lastName = doctorEntity.getLastName();
        doctorDTO.specialization = doctorEntity.getSpecializations().stream()
                .map(SpecializationEntity::getName)
                .toList();
        return doctorDTO;
    }
}
