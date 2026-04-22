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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_Clinic_User")
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    @Getter @Setter
    private String email;

    @Column(nullable = false, unique = true, length = 50)
    @Getter @Setter
    private String username;

    @Column(nullable = false)
    @Getter @Setter
    private String password;

    @Column(name = "enabled", nullable = false)
    @Getter @Setter
    private boolean enabled = true;

    @Getter @Setter
    private Timestamp createdAt;

    @Getter @Setter
    private Timestamp updatedAt;

    @Getter @Setter
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_Role")
    private RoleEntity role;

    public UserEntity(){}

    public UserEntity(Long id, String email, String username, String password, boolean enabled, Timestamp createdAt, Timestamp updatedAt, RoleEntity role) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.password = password;
        this.enabled = enabled;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.role = role;
    }
}
