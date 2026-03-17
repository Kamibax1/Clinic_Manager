package com.example.model.dto.profile;

import com.example.model.entity.PatientEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

public class PatientDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter
    private Long id;

    @Getter @Setter
    private String fullName;

    @Getter @Setter
    private String dateOfBirth;

    @Getter @Setter
    private String gender;

    @Getter @Setter
    private String phoneNumber;

    @Getter @Setter
    private String email;

    public static PatientDTO fromEntity(PatientEntity patientEntity) {
        PatientDTO patientDTO = new PatientDTO();
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
