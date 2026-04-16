package com.example.model.dto;

import com.example.model.entity.SpecializationEntity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

public class SpecializationDTO {
    @Getter @Setter
    @Id
    private Long id;

    @Getter @Setter
    private String name;

    public SpecializationDTO() {
    }

    public SpecializationDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public static SpecializationDTO fromEntity(SpecializationEntity entity) {
        return new SpecializationDTO(entity.getId(), entity.getName());
    }

    public static SpecializationEntity toEntity(SpecializationDTO dto) {
        return new SpecializationEntity(dto.getId(), dto.getName());
    }
}
