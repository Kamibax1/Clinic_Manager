package com.example.model.dto;

import com.example.model.entity.DoctorEntity;
import com.example.model.entity.SpecializationEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class DoctorsDTO {

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

    public static DoctorsDTO fromEntity(DoctorEntity doctorEntity) {
        DoctorsDTO doctorsDTO = new DoctorsDTO();
        doctorsDTO.firstName = doctorEntity.getFirstName();
        doctorsDTO.lastName = doctorEntity.getLastName();
        doctorsDTO.specialization = doctorEntity.getSpecializations().stream()
                .map(SpecializationEntity::getName)
                .toList();
        return doctorsDTO;
    }
}
