package com.YagoRueda.WorkoutBuddy.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class PetPasswordEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;


    private String token;
    private Boolean utilizado = false;
    private Boolean expirado = false;
    private Instant petition_date ;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    public Boolean getExpirado() {
        return expirado;
    }

    public void setExpirado(Boolean expirado) {
        this.expirado = expirado;
    }

    public Boolean getUtilizado() {
        return utilizado;
    }

    public void setUtilizado(Boolean utilizado) {
        this.utilizado = utilizado;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public long getId() {
        return id;
    }

    public Instant getPetition_date() {
        return petition_date;
    }

    public void setPetition_date(Instant petition_date) {
        this.petition_date = petition_date;
    }
}
