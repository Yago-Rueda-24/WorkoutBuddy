package com.YagoRueda.WorkoutBuddy.controller;


import com.YagoRueda.WorkoutBuddy.Service.UserService;
import com.YagoRueda.WorkoutBuddy.entity.UserEntity;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/trylog")
    public boolean logUser(@RequestBody UserEntity user) {
        return service.login(user);
    }

    @PostMapping("/signup")
    public boolean signUp(@Valid @RequestBody UserEntity user) {
        return service.signUp(user);
    }
}
