package com.example.model.dto;

import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

public class ProfileInfoResponse {
    @Id
    @Getter @Setter
    private String id;

    @Getter @Setter
    private String username;

    @Getter @Setter
    private String email;
}
