package com.example.model.dto.patient.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

public class UpdatePatientFullInformationRequest {
    @Getter @Setter
    @NotBlank(message = "Имя не может быть пустым")
    @Size(min = 3, max = 100, message = "Имя должно быть от 3 до 100 символов")
    @JsonProperty("first_name")
    private String firstName;

    @Getter @Setter
    @NotBlank(message = "Фамилия не может быть пустым")
    @Size(min = 3, max = 100, message = "Фамилия должна быть от 3 до 100 символов")
    @JsonProperty("last_name")
    private String lastName;

    @Getter @Setter
    @JsonProperty("middle_name")
    private String middleName;

    @Getter @Setter
    @NotNull(message = "Дата не может быть пустой")
    @JsonProperty("date_of_birth")
    private LocalDate dateOfBirth;

    @Getter @Setter
    @Pattern(
            regexp = "Мужской|Женский",
            message = "Пол должен быть 'Мужской' или 'Женский'"
    )
    private String gender;

    @Getter @Setter
    @JsonProperty("phone_number")
    @Pattern(
            regexp = "^(\\+7|8) \\(\\d{3}\\) \\d{3}-\\d{2}-\\d{2}$",
            message = "Номер телефона должен быть в формате: +7 (XXX) XXX-XX-XX или 8 (XXX) XXX-XX-XX"
    )
    private String phoneNumber;

    public UpdatePatientFullInformationRequest() {
    }

    public UpdatePatientFullInformationRequest(String firstName, String lastName, String middleName, LocalDate dateOfBirth, String gender, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
    }
}
