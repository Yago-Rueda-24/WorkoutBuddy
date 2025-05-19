package com.YagoRueda.WorkoutBuddy.controller;

import com.YagoRueda.WorkoutBuddy.DTO.RoutineDTO;
import com.YagoRueda.WorkoutBuddy.Service.RoutineService;
import com.YagoRueda.WorkoutBuddy.Service.UserService;
import com.YagoRueda.WorkoutBuddy.entity.RoutineEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/routine")
public class RoutineController {

    private final RoutineService service;

    public RoutineController(RoutineService service) {
        this.service = service;
    }

    @GetMapping("/show/{username}")
    public ResponseEntity<?> showRoutine(@PathVariable String username){
        if (username == null || username.trim().isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("message", "El nombre de usuario no puede estar vacío."));
        }
        List<RoutineEntity> routines = service.obtainRoutinesByUsername(username);
        if (routines!=null) {
            ArrayList<RoutineEntity> entities=  new ArrayList<>(routines);
            List<RoutineDTO> returnList = entities.stream().map((routine)->{
                RoutineDTO dto = new RoutineDTO();
                dto.setId(routine.getId());
                dto.setName(routine.getName());
                dto.setExercises(routine.getExercises());
                return dto;
            }).toList();


            return ResponseEntity.ok().body(returnList);

        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", "Error al buscar en la lista"));
        }

    }

    @PostMapping("/add/{username}")
    public ResponseEntity<?> addRoutine(@PathVariable String username, @RequestBody RoutineDTO dto){
        boolean success = service.addRoutine(username,dto);
        if(success){
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("mesagge","Creacion correcta"));

        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("mesagge","Error en la corrección"));
        }

    }


}
