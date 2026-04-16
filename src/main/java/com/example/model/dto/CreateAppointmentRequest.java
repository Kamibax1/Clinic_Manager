package com.example.model.dto;

import com.example.model.entity.AppointmentEntity;
import com.example.model.entity.StatusEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

public class CreateAppointmentRequest {
    @Id
    @Getter @Setter
    @JsonProperty("id_create_appointment")
    private Long id;

    @Getter @Setter
    private LocalDate date;

    @Getter @Setter
    private LocalTime time;

    @Getter @Setter
    private String symptoms;

    @Getter @Setter
    private PatientShortInfoResponse patient;

    @Getter @Setter
    private DoctorShortInfoResponse doctor;

    @Getter @Setter
    private StatusEntity status;

    public CreateAppointmentRequest() {
    }

    public CreateAppointmentRequest(Long id, LocalDate date, LocalTime time, String symptoms, PatientShortInfoResponse patient, DoctorShortInfoResponse doctor, StatusEntity status) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.symptoms = symptoms;
        this.patient = patient;
        this.doctor = doctor;
        this.status = status;
    }

    public static CreateAppointmentRequest fromEntity(AppointmentEntity entity) {
        CreateAppointmentRequest dto = new CreateAppointmentRequest();
        dto.setId(entity.getId());
        dto.date = entity.getDate();
        dto.time = entity.getTime();
        dto.symptoms = entity.getSymptoms();
        dto.patient = PatientShortInfoResponse.fromEntity(entity.getPatient());
        dto.doctor = DoctorShortInfoResponse.fromEntity(entity.getDoctor());
        dto.status = entity.getStatus();
        return dto;
    }

    public static AppointmentEntity toEntity(CreateAppointmentRequest dto) {
        AppointmentEntity entity = new AppointmentEntity();
        entity.setId(dto.getId());
        entity.setDate(dto.getDate());
        entity.setTime(dto.getTime());
        entity.setSymptoms(dto.symptoms);
        entity.setPatient(PatientShortInfoResponse.toEntity(dto.getPatient()));
        entity.setDoctor(DoctorShortInfoResponse.toEntity(dto.getDoctor()));
        entity.setStatus(dto.status);
        return entity;
    }
}
