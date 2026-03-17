package com.example.model.dto;

import com.example.model.entity.DoctorEntity;
import com.example.model.entity.SpecializationEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class DoctorDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter
    private Long id;

    @Getter @Setter
    private String firstName;

    @Getter @Setter
    private String lastName;

    @Getter @Setter
    private List<String> specialization;

    public static DoctorDTO fromEntity(DoctorEntity doctorEntity) {
        DoctorDTO doctorDTO = new DoctorDTO();
        doctorDTO.firstName = doctorEntity.getFirstName();
        doctorDTO.lastName = doctorEntity.getLastName();
        doctorDTO.specialization = doctorEntity.getSpecializations().stream()
                .map(SpecializationEntity::getName)
                .toList();
        return doctorDTO;
    }
}
