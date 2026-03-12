package com.example.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Table(name = "doctor")
public class DoctorEntity {
    @Id
    @Getter @Setter
    private Long id;

    @Getter @Setter
    private String firstName;

    @Getter @Setter
    private String lastName;

    @Getter @Setter
    private int experienceYears;

    @Getter @Setter
    private Timestamp createdAt;

    @Getter @Setter
    private Timestamp updatedAt;

    @OneToOne
    @JoinColumn(name = "id_user")
    private UserEntity user;

    public DoctorEntity() {}

    public DoctorEntity(Long id, String firstName, String lastName, int experienceYears, Timestamp createdAt, Timestamp updatedAt, UserEntity user) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.experienceYears = experienceYears;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.user = user;
    }
}
