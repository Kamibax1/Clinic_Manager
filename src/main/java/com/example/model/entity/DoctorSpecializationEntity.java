package com.example.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "doctor_specialization")
public class DoctorSpecializationEntity {
    @Id
    @Getter @Setter
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_doctor")
    private DoctorEntity doctorEntity;

    @ManyToOne
    @JoinColumn(name = "id_doctor_specialization")
    private DoctorSpecializationEntity doctorSpecializationEntity;

    public DoctorSpecializationEntity() {}

    public DoctorSpecializationEntity(Long id, DoctorEntity doctorEntity, DoctorSpecializationEntity doctorSpecializationEntity) {
        this.id = id;
        this.doctorEntity = doctorEntity;
        this.doctorSpecializationEntity = doctorSpecializationEntity;
    }
}
