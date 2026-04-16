package com.example.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

public class HomeStatsResponse {

    @Getter @Setter
    @JsonProperty("count_appointments_today")
    private int countAppointmentsToday;

    @Getter @Setter
    @JsonProperty("count_patients")
    private int countPatients;

    @Getter @Setter
    @JsonProperty("count_doctors")
    private int countDoctors;

    @Getter @Setter
    @JsonProperty("count_appointments_completed")
    private int countAppointmentsCompleted;

    public HomeStatsResponse() {
    }

    public HomeStatsResponse(int countAppointmentsToday, int countPatients, int countDoctors, int countAppointmentsCompleted) {
        this.countAppointmentsToday = countAppointmentsToday;
        this.countPatients = countPatients;
        this.countDoctors = countDoctors;
        this.countAppointmentsCompleted = countAppointmentsCompleted;
    }
}
