package com.example.model.dto.profile;

import com.example.model.entity.PatientEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

public class PatientDataDTO {

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

    public static PatientDataDTO fromEntity(PatientEntity patientEntity) {
        PatientDataDTO patientDataDTO = new PatientDataDTO();
        patientDataDTO.fullName = patientEntity.getFullName();
        patientDataDTO.dateOfBirth = patientEntity.getDateOfBirth();
        patientDataDTO.gender = patientEntity.getGender();
        patientDataDTO.phoneNumber = patientEntity.getPhoneNumber();
        patientDataDTO.email = patientEntity.getUser().getEmail();

        return patientDataDTO;
    }


    public static PatientEntity updateMap(PatientEntity patientEntity, PatientDataDTO patientDataDTO) {
        patientEntity.setFullName(patientDataDTO.fullName);
        patientEntity.setDateOfBirth(patientDataDTO.dateOfBirth);
        patientEntity.setPhoneNumber(patientDataDTO.phoneNumber);
        return patientEntity;
    }
}
