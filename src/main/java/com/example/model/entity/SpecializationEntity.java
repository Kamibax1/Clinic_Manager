package com.example.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "specialization")
public class SpecializationEntity {
    @Id
    @Getter @Setter
    private Long id;

    @Getter @Setter
    private String name;

    public SpecializationEntity() {}

    public SpecializationEntity(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
