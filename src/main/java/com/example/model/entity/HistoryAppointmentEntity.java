package com.example.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table
public class HistoryAppointmentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter
    private Long id;

    @Getter @Setter
    @OneToMany
    @JoinColumn(name = "id_Appointment")
    private List<AppointmentEntity> appointments;

    public HistoryAppointmentEntity() {
    }

    public HistoryAppointmentEntity(Long id, List<AppointmentEntity> appointments) {
        this.id = id;
        this.appointments = appointments;
    }
}
