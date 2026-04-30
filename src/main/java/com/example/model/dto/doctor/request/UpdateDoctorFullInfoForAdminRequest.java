package com.example.model.dto.doctor.request;

import com.example.model.dto.SpecializationDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

public class UpdateDoctorFullInfoForAdminRequest {
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
    @NotNull(message = "Опыт работы должен быть числом")
    @Min(value = 0, message = "Опыт не может быть отрицательным")
    @Max(value = 100, message = "Опыт не может быть более 100 лет")
    @JsonProperty("experience_years")
    private int experienceYears;

    @Getter @Setter
    @JsonProperty("phone_number")
    @Pattern(
            regexp = "^(\\+7|8) \\(\\d{3}\\) \\d{3}-\\d{2}-\\d{2}$",
            message = "Номер телефона должен быть в формате: +7 (XXX) XXX-XX-XX или 8 (XXX) XXX-XX-XX"
    )
    private String phoneNumber;

    @Getter @Setter
    private Set<SpecializationDTO> specializations;

    public UpdateDoctorFullInfoForAdminRequest() {
    }

    public UpdateDoctorFullInfoForAdminRequest(String firstName, String lastName, String middleName, int experienceYears, String phoneNumber, Set<SpecializationDTO> specializations) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.experienceYears = experienceYears;
        this.phoneNumber = phoneNumber;
        this.specializations = specializations;
    }
}
