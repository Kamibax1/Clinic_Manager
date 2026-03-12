package com.example.model.dto;

import com.example.model.entity.DoctorSpecializationEntity;
import lombok.Getter;
import lombok.Setter;

public class DoctorsDTO {

    @Getter @Setter
    private String firstName;

    @Getter @Setter
    private String lastName;

    @Getter @Setter
    private String specialization;

    public static DoctorsDTO fromEntity(DoctorSpecializationEntity doctorSpecializationEntity) {
        DoctorsDTO doctorsDTO = new DoctorsDTO();
        doctorsDTO.firstName = doctorSpecializationEntity.getDoctorEntity().getFirstName();
        doctorsDTO.lastName = doctorSpecializationEntity.getDoctorEntity().getLastName();
        doctorsDTO.specialization = doctorSpecializationEntity.getSpecializationEntity().getName();
        return doctorsDTO;
    }
}
