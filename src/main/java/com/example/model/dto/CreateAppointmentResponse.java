package com.example.model.dto;

import com.example.model.entity.AppointmentEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

public class CreateAppointmentResponse {
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
    private StatusDTO status;

    @Getter @Setter
    private PatientShortInfoResponse patient;

    @Getter @Setter
    private DoctorShortInfoResponse doctor;


    public CreateAppointmentResponse() {
    }

    public CreateAppointmentResponse(Long id, LocalDate date, LocalTime time, String symptoms, StatusDTO status, PatientShortInfoResponse patient, DoctorShortInfoResponse doctor) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.symptoms = symptoms;
        this.status = status;
        this.patient = patient;
        this.doctor = doctor;
    }

    public static CreateAppointmentResponse fromEntity(AppointmentEntity entity) {
        CreateAppointmentResponse dto = new CreateAppointmentResponse();
        dto.id = entity.getId();
        dto.date = entity.getDate();
        dto.time = entity.getTime();
        dto.symptoms = entity.getSymptoms();
        dto.status = StatusDTO.fromEntity(entity.getStatus());
        dto.patient = PatientShortInfoResponse.fromEntity(entity.getPatient());
        dto.doctor = DoctorShortInfoResponse.fromEntity(entity.getDoctor());
        return dto;
    }
}
