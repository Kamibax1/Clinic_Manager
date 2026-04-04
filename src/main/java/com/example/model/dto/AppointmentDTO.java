package com.example.model.dto;

import com.example.model.entity.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

public class AppointmentDTO {
    @Id
    @Getter @Setter
    @JsonProperty("id_appointment")
    private Long id;

    @Getter @Setter
    @JsonProperty("date_time")
    private LocalDateTime dateTime;

    @Getter @Setter
    private String symptoms;

    @Getter @Setter
    private Long id_status;

    @Getter @Setter
    private Long id_patient;

    @Getter @Setter
    private Long id_doctor;

    public static AppointmentDTO fromEntity(AppointmentEntity entity) {
        AppointmentDTO dto = new AppointmentDTO();
        dto.id = entity.getId();
        dto.dateTime = entity.getDateTime();
        dto.symptoms = entity.getSymptoms();
        dto.id_status = entity.getStatus().getId();
        dto.id_patient = entity.getPatient().getId();
        dto.id_doctor = entity.getDoctor().getId();
        return dto;
    }

    public static AppointmentEntity toEntity(AppointmentDTO dto, StatusEntity statusEntity, PatientEntity patientEntity, DoctorEntity doctorEntity) {
        AppointmentEntity entity = new AppointmentEntity();
        return updateMap(entity, dto, statusEntity, patientEntity, doctorEntity);
    }

    public static AppointmentEntity updateMap(AppointmentEntity entity, AppointmentDTO dto, StatusEntity status, PatientEntity patient, DoctorEntity doctor) {
        entity.setId(dto.id);
        entity.setDateTime(dto.dateTime);
        entity.setSymptoms(dto.symptoms);
        entity.setStatus(status);
        entity.setPatient(patient);
        entity.setDoctor(doctor);
        return entity;
    }
}
