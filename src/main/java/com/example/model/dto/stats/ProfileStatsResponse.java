package com.example.model.dto.stats;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

public class ProfileStatsResponse {
    @Getter @Setter
    @JsonProperty("count_appointment")
    private int countAppointment;

    @Getter @Setter
    @JsonProperty("date_of_registration")
    private LocalDate dateOfRegistration;

    @Getter @Setter
    @JsonProperty("count_current_appointment")
    private int countCurrentAppointment;

    public ProfileStatsResponse() {
    }

    public ProfileStatsResponse(int countAppointment, LocalDate dateOfRegistration, int countCurrentAppointment) {
        this.countAppointment = countAppointment;
        this.dateOfRegistration = dateOfRegistration;
        this.countCurrentAppointment = countCurrentAppointment;
    }
}
