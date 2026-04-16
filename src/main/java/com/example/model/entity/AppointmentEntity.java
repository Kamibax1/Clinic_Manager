package com.example.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "Appointment")
public class AppointmentEntity {
    @Id
    @Getter @Setter
    @Column(name = "id_Appointment")
    private Long id;

    @Getter @Setter
    private LocalDate date;

    @Getter @Setter
    private LocalTime time;

    @Getter @Setter
    private String symptoms;

    @Getter @Setter
    private Timestamp createdAt;

    @Getter @Setter
    private Timestamp updatedAt;

    @Getter @Setter
    @ManyToOne
    @JoinColumn(name = "id_status")
    private StatusEntity status;

    @Getter @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_patient")
    private PatientEntity patient;

    @Getter @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_doctor")
    private DoctorEntity doctor;

    public AppointmentEntity() {}

    public AppointmentEntity(Long id, LocalDate date, LocalTime time, String symptoms, Timestamp createdAt, Timestamp updatedAt, StatusEntity status, PatientEntity patient, DoctorEntity doctor) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.symptoms = symptoms;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.status = status;
        this.patient = patient;
        this.doctor = doctor;
    }
}
