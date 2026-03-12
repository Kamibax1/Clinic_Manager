package com.example.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDate;

@Entity
@Table(name = "appointment")
public class AppointmentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter
    private Long id;

    @Getter @Setter
    private LocalDate appointment_date;

    @ManyToOne
    @JoinColumn(name = "id_status")
    private StatusEntity status;

    @Getter @Setter
    private String symptoms;

    @Getter @Setter
    private Timestamp createdAt;

    @Getter @Setter
    private Timestamp updatedAt;

    @ManyToOne
    @JoinColumn(name = "id_doctor_specialization")
    private DoctorSpecializationEntity doctorSpecializationEntity;

    @ManyToOne
    @JoinColumn(name = "id_patient")
    private PatientEntity patientEntity;

    public AppointmentEntity() {}

    public AppointmentEntity(Long id, LocalDate appointment_date, StatusEntity status, String symptoms, Timestamp createdAt, Timestamp updatedAt, DoctorSpecializationEntity doctorSpecializationEntity, PatientEntity patientEntity) {
        this.id = id;
        this.appointment_date = appointment_date;
        this.status = status;
        this.symptoms = symptoms;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.doctorSpecializationEntity = doctorSpecializationEntity;
        this.patientEntity = patientEntity;
    }
}
