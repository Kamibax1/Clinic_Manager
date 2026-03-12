package com.example.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "specialization")
public class SpecializationEntity {
    @Id
    @Getter @Setter
    private Long id;

    @Getter @Setter
    private String name;

    @Getter @Setter
    @ManyToMany(mappedBy = "specializations")
    private Set<DoctorEntity> doctors = new HashSet<>();

    public SpecializationEntity() {}

    public SpecializationEntity(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
