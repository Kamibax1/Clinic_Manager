package com.example.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "appointment")
public class AppointmentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter
    private Long id;

    @Getter @Setter
    private LocalDateTime dateTime;

    @Getter @Setter
    @ManyToOne
    @JoinColumn(name = "id_status")
    private StatusEntity status;

    @Getter @Setter
    private String symptoms;

    @Getter @Setter
    private Timestamp createdAt;

    @Getter @Setter
    private Timestamp updatedAt;

    @Getter @Setter
    @ManyToOne
    @JoinColumn(name = "id_doctor")
    private DoctorEntity doctor;

    @Getter @Setter
    @ManyToOne
    @JoinColumn(name = "id_patient")
    private PatientEntity patient;

    public AppointmentEntity() {}

    public AppointmentEntity(Long id, LocalDateTime dateTime, StatusEntity status, String symptoms, Timestamp createdAt, Timestamp updatedAt, DoctorEntity doctorEntity, PatientEntity patientEntity) {
        this.id = id;
        this.dateTime = dateTime;
        this.status = status;
        this.symptoms = symptoms;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.doctor = doctorEntity;
        this.patient = patientEntity;
    }
}
