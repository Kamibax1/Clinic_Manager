package com.example.model.dto;

import com.example.model.entity.AppointmentEntity;
import com.example.model.entity.StatusEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

public class CreateAppointmentRequest {
    @Id
    @Getter @Setter
    @JsonProperty("id_create_appointment")
    private Long id;

    @Getter @Setter
    @JsonProperty("date_time")
    private LocalDateTime dateTime;

    @Getter @Setter
    private String symptoms;

    @Getter @Setter
    private PatientDTO patient;

    @Getter @Setter
    private DoctorDTO doctor;

    @Getter @Setter
    private StatusEntity status;

    public CreateAppointmentRequest() {
    }

    public CreateAppointmentRequest(Long id, LocalDateTime dateTime, String symptoms, PatientDTO patient, DoctorDTO doctor, StatusEntity status) {
        this.id = id;
        this.dateTime = dateTime;
        this.symptoms = symptoms;
        this.patient = patient;
        this.doctor = doctor;
        this.status = status;
    }

    public static CreateAppointmentRequest fromEntity(AppointmentEntity entity) {
        CreateAppointmentRequest dto = new CreateAppointmentRequest();
        dto.setId(entity.getId());
        dto.dateTime = entity.getDateTime();
        dto.symptoms = entity.getSymptoms();
        dto.patient = PatientDTO.fromEntity(entity.getPatient());
        dto.doctor = DoctorDTO.fromEntity(entity.getDoctor());
        dto.status = entity.getStatus();
        return dto;
    }

    public static AppointmentEntity toEntity(CreateAppointmentRequest dto) {
        AppointmentEntity entity = new AppointmentEntity();
        entity.setId(dto.getId());
        entity.setDateTime(dto.dateTime);
        entity.setSymptoms(dto.symptoms);
        entity.setPatient(PatientDTO.toEntity(dto.patient));
        entity.setDoctor(DoctorDTO.toEntity(dto.doctor));
        entity.setStatus(dto.status);
        return entity;
    }
}
