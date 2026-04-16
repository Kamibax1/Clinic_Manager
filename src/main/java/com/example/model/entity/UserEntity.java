package com.example.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Table(name = "Clinic_User")
public class UserEntity {
    @Id
    @Getter @Setter
    @Column(name = "id_Clinic_User")
    private Long id;

    @Getter @Setter
    private String email;

    @Getter @Setter
    private String username;

    @Getter @Setter
    private String password;

    @Getter @Setter
    private Timestamp createdAt;

    @Getter @Setter
    private Timestamp updatedAt;

    @Getter @Setter
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_Role")
    private RoleEntity role;

    public UserEntity(){}

    public UserEntity(Long id, String email, String username, String password, Timestamp createdAt, Timestamp updatedAt, RoleEntity role) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.password = password;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.role = role;
    }
}
