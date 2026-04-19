package com.example.model.dto;

import com.example.model.entity.StatusEntity;
import com.example.model.enums.StatusEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

public class StatusDTO {
    @Id
    @Getter @Setter
    @JsonProperty("id_status")
    private Long id;

    @Getter @Setter
    private StatusEnum status;

    public StatusDTO() {
    }

    public StatusDTO(Long id, StatusEnum status) {
        this.id = id;
        this.status = status;
    }

    public static StatusDTO fromEntity(StatusEntity entity) {
        return new StatusDTO(entity.getId(), entity.getStatus());
    }

    public static StatusEntity toEntity(StatusDTO dto) {
        return new StatusEntity(dto.id, dto.status);
    }
}
