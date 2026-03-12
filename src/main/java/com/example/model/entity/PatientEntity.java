package com.example.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Table(name = "patient")
public class PatientEntity {
    @Id
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
    private Timestamp createdAt;

    @Getter @Setter
    private Timestamp updatedAt;

    @Getter @Setter
    @OneToOne
    @JoinColumn(name = "id_user")
    private UserEntity user;

    @Getter @Setter
    @OneToOne
    @JoinColumn(name = "id_History_Appointment")
    private AppointmentEntity appointment;

    public PatientEntity() {}

    public PatientEntity(Long id, String fullName, String dateOfBirth, String gender, String phoneNumber, Timestamp createdAt, Timestamp updatedAt, UserEntity user, AppointmentEntity appointment) {
        this.id = id;
        this.fullName = fullName;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.user = user;
        this.appointment = appointment;
    }
}
