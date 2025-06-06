package com.YagoRueda.WorkoutBuddy.DTO;

import jakarta.validation.constraints.NotBlank;


public class RecoverPasswordDTO {

    @NotBlank(message = "La contraseña es obligatoria")
    private String password;
    @NotBlank(message = "La contraseña repetida es obligatoria")
    private String repeatpassword;
    @NotBlank(message = "El usuario es obligatorio")
    private String username;
    @NotBlank(message = "El token es obligatorio")
    private String token;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRepeatpassword() {
        return repeatpassword;
    }

    public void setRepeatpassword(String repeatpassword) {
        this.repeatpassword = repeatpassword;
    }
}
