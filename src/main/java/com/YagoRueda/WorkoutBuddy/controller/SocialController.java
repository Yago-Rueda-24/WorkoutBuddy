package com.YagoRueda.WorkoutBuddy.controller;

import com.YagoRueda.WorkoutBuddy.DTO.UserInfoDTO;
import com.YagoRueda.WorkoutBuddy.Service.UserService;
import com.YagoRueda.WorkoutBuddy.entity.UserEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/social")
public class SocialController {

    private final UserService userservice;

    public SocialController(UserService userservice) {
        this.userservice = userservice;
    }


    @GetMapping("/users")
    public ResponseEntity<?> listUsers(@RequestParam String exclude) {

        List<UserInfoDTO> users = userservice.listLimitedUsers(exclude);
        if (users != null) {
            return ResponseEntity.status(HttpStatus.OK).body(users);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", "Error interno"));
    }

    @GetMapping("/users/{filteredname}")
    public ResponseEntity<?> listUsers(@PathVariable String filteredname, @RequestParam String exclude) {

        List<UserInfoDTO> users = userservice.listFilteredLimitedUsers(filteredname,exclude);
        if (users != null) {
            return ResponseEntity.status(HttpStatus.OK).body(users);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", "Error interno"));
    }
}
