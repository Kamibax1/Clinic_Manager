package com.example.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDate;

@Entity
@Table(name = "Patient")
public class PatientEntity {
    @Id
    @Getter @Setter
    @Column(name = "id_Patient")
    private Long id;

    @Getter @Setter
    private String fullName;

    @Getter @Setter
    private LocalDate dateOfBirth;

    @Getter @Setter
    private String gender;

    @Getter @Setter
    private String phoneNumber;

    @Getter @Setter
    private Timestamp createdAt;

    @Getter @Setter
    private Timestamp updatedAt;

    @Getter @Setter
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_Clinic_User")
    private UserEntity user;

    public PatientEntity() {}

    public PatientEntity(Long id, String fullName, LocalDate dateOfBirth, String gender, String phoneNumber, Timestamp createdAt, Timestamp updatedAt, UserEntity user) {
        this.id = id;
        this.fullName = fullName;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.user = user;
    }
}
