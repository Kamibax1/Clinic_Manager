package com.example.model.entity;

import com.example.model.enums.StatusEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "status")
public class StatusEntity {
    @Id
    @Getter @Setter
    private Long id;

    @Getter @Setter
    @Enumerated(EnumType.STRING)
    private StatusEnum status;

    public StatusEntity() {
    }

    public StatusEntity(Long id, StatusEnum status) {
        this.id = id;
        this.status = status;
    }
}
