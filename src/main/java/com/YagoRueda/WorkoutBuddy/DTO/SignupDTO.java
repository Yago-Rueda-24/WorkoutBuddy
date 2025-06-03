package com.YagoRueda.WorkoutBuddy.DTO;

import jakarta.validation.constraints.NotBlank;

public class SignupDTO {

    @NotBlank(message = "El username es obligatorio")
    private String username;
    @NotBlank(message = "La password es obligatoria")
    private String password;
    @NotBlank(message = "La password es obligatoria")
    private String passwordrepeat;
    @NotBlank(message = "El email es obligatorio")
    private String email;

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

    public String getPasswordrepeat() {return passwordrepeat; }

    public void setPasswordrepeat(String passwordrepeat) { this.passwordrepeat = passwordrepeat; }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
