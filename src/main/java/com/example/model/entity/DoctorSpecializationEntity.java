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

    @Getter @Setter
    @ManyToOne
    @JoinColumn(name = "id_doctor")
    private DoctorEntity doctorEntity;

    @Getter @Setter
    @ManyToOne
    @JoinColumn(name = "id_doctor_specialization")
    private SpecializationEntity SpecializationEntity;

    public DoctorSpecializationEntity() {}

    public DoctorSpecializationEntity(Long id, DoctorEntity doctorEntity, SpecializationEntity SpecializationEntity) {
        this.id = id;
        this.doctorEntity = doctorEntity;
        this.SpecializationEntity = SpecializationEntity;
    }
}
