package com.example.model.entity;

import com.example.model.enums.RoleEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "role")
public class RoleEntity {
    @Id
    @Getter @Setter
    private Long id;

    @Getter @Setter
    @Enumerated(EnumType.STRING)
    private RoleEnum name;

    public RoleEntity() {}

    public RoleEntity(Long id, RoleEnum name) {
        this.id = id;
        this.name = name;
    }
}
