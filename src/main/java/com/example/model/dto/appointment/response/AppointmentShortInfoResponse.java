package com.example.model.dto.appointment.response;

import com.example.model.dto.StatusDTO;
import com.example.model.entity.AppointmentEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

public class AppointmentShortInfoResponse {
    @Id
    @Setter @Getter
    @JsonProperty("id_appointment_short_info")
    private Long id;

    @Getter @Setter
    private LocalDate date;

    @Getter @Setter
    private LocalTime time;

    @Getter @Setter
    private String symptoms;

    @Getter @Setter
    @JsonProperty("doctor_name")
    private String doctorName;

    @Getter @Setter
    private StatusDTO status;

    public AppointmentShortInfoResponse() {
    }

    public AppointmentShortInfoResponse(Long id, LocalDate date, LocalTime time, String symptoms, String doctorName, StatusDTO status) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.symptoms = symptoms;
        this.doctorName = doctorName;
        this.status = status;
    }

    public static AppointmentShortInfoResponse fromEntity(AppointmentEntity entity) {
        AppointmentShortInfoResponse dto = new AppointmentShortInfoResponse();
        dto.id = entity.getId();
        dto.date = entity.getDate();
        dto.time = entity.getTime();
        dto.doctorName = entity.getDoctor().getLastName() + " " + entity.getDoctor().getFirstName() + " " + entity.getDoctor().getMiddleName();
        dto.symptoms = entity.getSymptoms();
        dto.status = StatusDTO.fromEntity(entity.getStatus());
        return dto;
    }
}
