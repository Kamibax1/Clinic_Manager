package com.example.model.dto;

import com.example.model.entity.AppointmentEntity;
import com.example.model.entity.StatusEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

public class AppointmentFullDTO {
    @Id
    @Getter @Setter
    @JsonProperty("id_appointment_full")
    private Long id;

    @Getter @Setter
    @JsonProperty("date_time")
    private LocalDateTime dateTime;

    @Getter @Setter
    private PatientDTO patient;

    @Getter @Setter
    private DoctorDTO doctor;

    @Getter @Setter
    private StatusEntity status;


    public AppointmentFullDTO() {
    }

    public AppointmentFullDTO(Long id, LocalDateTime dateTime, PatientDTO patient, DoctorDTO doctor, StatusEntity status) {
        this.id = id;
        this.dateTime = dateTime;
        this.patient = patient;
        this.doctor = doctor;
        this.status = status;
    }

    public static AppointmentFullDTO fromEntity(AppointmentEntity entity) {
        AppointmentFullDTO dto = new AppointmentFullDTO();
        dto.id = entity.getId();
        dto.dateTime = entity.getDateTime();
        dto.patient = PatientDTO.fromEntity(entity.getPatient());
        dto.doctor = DoctorDTO.fromEntity(entity.getDoctor());
        dto.status = entity.getStatus();
        return dto;
    }
}
