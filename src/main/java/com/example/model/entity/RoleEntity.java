package com.example.model.entity;

import com.example.model.enums.RoleEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Role")
public class RoleEntity {
    @Id
    @Getter @Setter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_Role")
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
