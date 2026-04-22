package com.example.model.dto.appointment.response;

import com.example.model.dto.doctor.response.DoctorFullInformationResponse;
import com.example.model.dto.patient.response.PatientFullInformationResponse;
import com.example.model.dto.StatusDTO;
import com.example.model.entity.AppointmentEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

public class AppointmentFullInformationResponse {
    @Getter @Setter
    private LocalDate date;

    @Getter @Setter
    private LocalTime time;

    @Getter @Setter
    private String symptoms;

    @Getter @Setter
    private StatusDTO status;

    @Getter @Setter
    @JsonProperty("patient_full_information")
    private PatientFullInformationResponse patient;

    @Getter @Setter
    @JsonProperty("doctor_full_information")
    private DoctorFullInformationResponse doctor;

    public AppointmentFullInformationResponse() {
    }

    public AppointmentFullInformationResponse(LocalDate date, LocalTime time, String symptoms, StatusDTO status, PatientFullInformationResponse patient, DoctorFullInformationResponse doctor) {
        this.date = date;
        this.time = time;
        this.symptoms = symptoms;
        this.status = status;
        this.patient = patient;
        this.doctor = doctor;
    }

    public static AppointmentFullInformationResponse fromEntity(AppointmentEntity entity) {
        AppointmentFullInformationResponse dto = new AppointmentFullInformationResponse();
        dto.date = entity.getDate();
        dto.time = entity.getTime();
        dto.symptoms = entity.getSymptoms();
        dto.status = StatusDTO.fromEntity(entity.getStatus());
        dto.patient = PatientFullInformationResponse.fromEntity(entity.getPatient());
        dto.doctor = DoctorFullInformationResponse.fromEntity(entity.getDoctor());
        return dto;
    }
}
