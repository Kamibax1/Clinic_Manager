package com.example.model.dto.appointment.request;

import com.example.model.enums.StatusEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

public class UpdateAppointmentStatusRequest {
    @Getter @Setter
    @JsonProperty("id_doctor")
    private int doctorId;

    @Getter @Setter
    private StatusEnum status;

    public UpdateAppointmentStatusRequest() {
    }

    public UpdateAppointmentStatusRequest(int doctorId, StatusEnum status) {
        this.doctorId = doctorId;
        this.status = status;
    }
}
