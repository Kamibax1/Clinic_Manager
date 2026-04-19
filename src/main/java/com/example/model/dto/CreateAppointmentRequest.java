package com.example.model.dto;

import com.example.model.entity.AppointmentEntity;
import com.example.model.entity.DoctorEntity;
import com.example.model.entity.PatientEntity;
import com.example.model.entity.StatusEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

public class CreateAppointmentRequest {
    @NotNull(message = "Дата не может быть пустой")
    @FutureOrPresent(message = "Дата должна быть сегодня или в будущем")
    @Getter @Setter
    private LocalDate date;

    @NotNull(message = "Время не может быть пустым")
    @Getter @Setter
    private LocalTime time;

    @NotBlank(message = "Симптомы не могут быть пустыми")
    @Size(min = 5, max = 500, message = "Симптомы должны быть от 5 до 500 символов")
    @Getter @Setter
    private String symptoms;

    @Getter @Setter
    @JsonProperty("id_patient_short_information")
    private Long patientId;

    @Getter @Setter
    @JsonProperty("id_doctor_short_information")
    private Long doctorId;

    public CreateAppointmentRequest() {
    }

    public CreateAppointmentRequest(LocalDate date, LocalTime time, String symptoms, Long patientId, Long doctorId) {
        this.date = date;
        this.time = time;
        this.symptoms = symptoms;
        this.patientId = patientId;
        this.doctorId = doctorId;
    }

    public static CreateAppointmentRequest fromEntity(AppointmentEntity entity) {
        CreateAppointmentRequest dto = new CreateAppointmentRequest();
        dto.date = entity.getDate();
        dto.time = entity.getTime();
        dto.symptoms = entity.getSymptoms();
        dto.patientId = entity.getPatient().getId();
        dto.doctorId = entity.getDoctor().getId();
        return dto;
    }

    public static AppointmentEntity toEntity(CreateAppointmentRequest dto, StatusEntity status, PatientEntity patient, DoctorEntity doctor) {
        AppointmentEntity entity = new AppointmentEntity();
        entity.setDate(dto.date);
        entity.setTime(dto.time);
        entity.setSymptoms(dto.symptoms);
        entity.setStatus(status);
        entity.setPatient(patient);
        entity.setDoctor(doctor);
        return entity;
    }
}
