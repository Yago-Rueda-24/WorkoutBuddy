package com.YagoRueda.WorkoutBuddy.controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HolaController {

    @GetMapping("/hola")
    public String decirHola() {
        return "¡Hola desde Spring Boot!";
    }

}
