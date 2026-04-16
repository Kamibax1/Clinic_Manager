package com.example.model.dto;

import com.example.model.entity.AppointmentEntity;
import com.example.model.entity.StatusEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

public class AppointmentInformationDTO {
    @Id
    @Getter @Setter
    @JsonProperty("id_appointment_information")
    private Long id;

    @JsonProperty("date_time")
    @Getter @Setter
    private LocalDateTime dateTime;

    @Getter @Setter
    private String symptoms;

    @Getter @Setter
    private StatusEntity status;

    @Getter @Setter
    private PatientDTO patient;

    @Getter @Setter
    private DoctorDTO doctor;

    public AppointmentInformationDTO() {
    }

    public AppointmentInformationDTO(Long id, LocalDateTime dateTime, String symptoms, StatusEntity status, PatientDTO patient, DoctorDTO doctor) {
        this.id = id;
        this.dateTime = dateTime;
        this.symptoms = symptoms;
        this.status = status;
        this.patient = patient;
        this.doctor = doctor;
    }

    public static AppointmentInformationDTO fromEntity(AppointmentEntity entity) {
        AppointmentInformationDTO dto = new AppointmentInformationDTO();
        dto.id = entity.getId();
        dto.dateTime = entity.getDateTime();
        dto.symptoms = entity.getSymptoms();
        dto.status = entity.getStatus();
        dto.patient = PatientDTO.fromEntity(entity.getPatient());
        dto.doctor = DoctorDTO.fromEntity(entity.getDoctor());
        return dto;
    }
}
