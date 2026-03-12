package com.example.model.dto.profile;

import com.example.model.entity.PatientEntity;
import lombok.Getter;
import lombok.Setter;

public class PatientDataDTO {

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

    public PatientDataDTO fromEntity(PatientEntity patientEntity) {
        PatientDataDTO patientDataDTO = new PatientDataDTO();
        patientDataDTO.fullName = patientEntity.getFullName();
        patientDataDTO.dateOfBirth = patientEntity.getDateOfBirth();
        patientDataDTO.gender = patientEntity.getGender();
        patientDataDTO.phoneNumber = patientEntity.getPhoneNumber();
        patientDataDTO.email = patientEntity.getUser().getEmail();

        return patientDataDTO;
    }
}
