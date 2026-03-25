package com.example.model.dto;

import com.example.model.entity.AppointmentEntity;
import com.example.model.entity.DoctorEntity;
import com.example.model.entity.PatientEntity;
import com.example.model.entity.StatusEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

public class AppointmentDTO {
    @Id
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

    public static AppointmentDTO fromEntity(AppointmentEntity appointmentEntity) {
        AppointmentDTO appointmentDTO = new AppointmentDTO();
        appointmentDTO.id = appointmentEntity.getId();
        appointmentDTO.dateTime = appointmentEntity.getDateTime();
        appointmentDTO.symptoms = appointmentEntity.getSymptoms();
        appointmentDTO.id_status = appointmentEntity.getStatus().getId();
        appointmentDTO.id_patient = appointmentEntity.getPatient().getId();
        appointmentDTO.id_doctor = appointmentEntity.getDoctor().getId();
        return appointmentDTO;
    }

    public static AppointmentEntity fromDTO(AppointmentDTO appointmentDTO, StatusEntity statusEntity, PatientEntity patientEntity, DoctorEntity doctorEntity) {
        AppointmentEntity appointmentEntity = new AppointmentEntity();
        return updateMap(appointmentEntity, appointmentDTO, statusEntity, patientEntity, doctorEntity);
    }

    public static AppointmentEntity updateMap(AppointmentEntity appointmentEntity, AppointmentDTO appointmentDTO, StatusEntity statusEntity, PatientEntity patientEntity, DoctorEntity doctorEntity) {
        appointmentEntity.setId(appointmentDTO.id);
        appointmentEntity.setDateTime(appointmentDTO.dateTime);
        appointmentEntity.setSymptoms(appointmentDTO.symptoms);
        appointmentEntity.setStatus(statusEntity);
        appointmentEntity.setPatient(patientEntity);
        appointmentEntity.setDoctor(doctorEntity);
        return appointmentEntity;
    }
}
