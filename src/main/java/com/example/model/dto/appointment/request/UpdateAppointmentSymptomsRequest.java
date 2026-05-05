package com.example.model.dto.appointment.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

public class UpdateAppointmentSymptomsRequest {
    @Getter @Setter
    @JsonProperty("id_doctor")
    private int doctorId;

    @Getter @Setter
    private String symptoms;

    public UpdateAppointmentSymptomsRequest() {
    }

    public UpdateAppointmentSymptomsRequest(int doctorId, String symptoms) {
        this.doctorId = doctorId;
        this.symptoms = symptoms;
    }
}
