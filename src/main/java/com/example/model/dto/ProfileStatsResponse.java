package com.example.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

public class ProfileStatsResponse {
    @Getter
    @Setter
    private int countAppointment;

    @Getter
    @Setter
    private LocalDate dateOfRegistration;

    @Getter
    @Setter
    private int countCurrentAppointment;

    public ProfileStatsResponse() {
    }

    public ProfileStatsResponse(int countAppointment, LocalDate dateOfRegistration, int countCurrentAppointment) {
        this.countAppointment = countAppointment;
        this.dateOfRegistration = dateOfRegistration;
        this.countCurrentAppointment = countCurrentAppointment;
    }
}
