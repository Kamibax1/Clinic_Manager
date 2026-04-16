package com.example.model.dto;

import com.example.model.entity.StatusEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

public class CreateAppointmentResponse {
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

    public CreateAppointmentResponse() {
    }

    public CreateAppointmentResponse(Long id, LocalDateTime dateTime, String symptoms, PatientDTO patient, DoctorDTO doctor, StatusEntity status) {
        this.id = id;
        this.dateTime = dateTime;
        this.symptoms = symptoms;
        this.patient = patient;
        this.doctor = doctor;
        this.status = status;
    }
}
