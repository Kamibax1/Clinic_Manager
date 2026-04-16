package com.example.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.Set;

@Entity
@Table(name = "Doctor")
public class DoctorEntity {
    @Id
    @Getter @Setter
    @Column(name = "id_Doctor")
    private Long id;

    @Getter @Setter
    private String firstName;

    @Getter @Setter
    private String lastName;

    @Getter @Setter
    private String middleName;

    @Getter @Setter
    private String phoneNumber;

    @Getter @Setter
    private int experienceYears;

    @Getter @Setter
    private Timestamp createdAt;

    @Getter @Setter
    private Timestamp updatedAt;

    @Getter @Setter
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_User")
    private UserEntity user;

    @Getter @Setter
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "doctor_specialization",
            joinColumns = @JoinColumn(name = "id_Doctor"),
            inverseJoinColumns = @JoinColumn(name = "id_Specialization")
    )
    private Set<SpecializationEntity> specializations;

    public DoctorEntity() {}

    public DoctorEntity(Long id, String firstName, String lastName, String middleName, String phoneNumber, int experienceYears, Timestamp createdAt, Timestamp updatedAt, UserEntity user, Set<SpecializationEntity> specializations) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.phoneNumber = phoneNumber;
        this.experienceYears = experienceYears;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.user = user;
        this.specializations = specializations;
    }
}
