package com.YagoRueda.WorkoutBuddy.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.List;
import java.util.Set;

@Entity
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotBlank(message = "El username es obligatorio")
    private String username;
    @NotBlank(message = "La password es obligatoria")
    private String password;
    @NotBlank(message = "El email es obligatorio")
    private String email;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<RoutineEntity> routines;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<PetPasswordEntity> petPasswords;
    @OneToMany(mappedBy = "followed", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<FollowEntity> followers;
    @OneToMany(mappedBy = "follower", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<FollowEntity> followed;


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
