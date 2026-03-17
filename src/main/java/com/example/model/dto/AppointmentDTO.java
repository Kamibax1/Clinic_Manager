package com.example.model.dto;

import com.example.model.entity.AppointmentEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

public class AppointmentDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter
    private Long id;

    @Getter @Setter
    private LocalDateTime dateTime;

    @Getter @Setter
    private String symptoms;

    @Getter @Setter
    private Long id_status;

    @Getter @Setter
    private Long id_patient;

    @Getter @Setter
    private Long id_doctor;

    public static AppointmentDTO from(AppointmentEntity appointmentEntity) {
        AppointmentDTO appointmentDTO = new AppointmentDTO();
        appointmentDTO.id = appointmentEntity.getId();
        appointmentDTO.dateTime = appointmentEntity.getDateTime();
        appointmentDTO.symptoms = appointmentEntity.getSymptoms();
        appointmentDTO.id_status = appointmentEntity.getStatus().getId();
        appointmentDTO.id_patient = appointmentEntity.getPatient().getId();
        appointmentDTO.id_doctor = appointmentEntity.getDoctor().getId();
        return appointmentDTO;
    }
}
