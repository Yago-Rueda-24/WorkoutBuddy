package com.YagoRueda.WorkoutBuddy.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

@Entity
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotBlank(message = "El username es obligatorio")
    private String username;
    @NotBlank(message = "La password es obligatoria")
    private String password;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private List<RoutineEntity> routines;

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
}
