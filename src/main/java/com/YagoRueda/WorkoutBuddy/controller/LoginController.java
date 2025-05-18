package com.YagoRueda.WorkoutBuddy.controller;


import com.YagoRueda.WorkoutBuddy.DTO.SignupDTO;
import com.YagoRueda.WorkoutBuddy.Service.UserService;
import com.YagoRueda.WorkoutBuddy.entity.UserEntity;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/login")
@Validated
public class LoginController {

    private final UserService service;

    public LoginController(UserService service) {
        this.service = service;
    }


    @GetMapping("/show")
    public List<UserEntity> showUsers() {
        return service.findAll();
    }

    @PostMapping("/trylog")
    public ResponseEntity<?> logUser(@RequestBody UserEntity user) {

        if (service.login(user)) {
            return ResponseEntity.ok().body(Map.of("message", "Inicio de sesión exitoso"));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Usuario o contraseña incorrectos"));
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@Valid @RequestBody SignupDTO signup) {

        switch (service.signUp(signup)) {
            //Creación correcta
            case 0:
                return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message", "Creación correcta"));

            //Password Incorrecta
            case 1:
                return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("message", "Las contraseñas no coincidenc"));

            //El usuario existe
            case 2:
                return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("message", "El nombre de usuario ya existe"));

            default:
                return null;
        }
    }
}
