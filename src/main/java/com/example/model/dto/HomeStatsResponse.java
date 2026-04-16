package com.example.model.dto;

import lombok.Getter;
import lombok.Setter;

public class HomeStatsResponse {

    @Getter @Setter
    private int countAppointmentsToday;

    @Getter @Setter
    private int countPatients;

    @Getter @Setter
    private int countDoctors;

    @Getter @Setter
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
