package com.YagoRueda.WorkoutBuddy.controller;

import com.YagoRueda.WorkoutBuddy.DTO.ModifyRoutineDTO;
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

    @GetMapping("/{username}")
    public ResponseEntity<?> showRoutine(@PathVariable String username) {
        if (username == null || username.trim().isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("message", "El nombre de usuario no puede estar vacío."));
        }
        List<RoutineEntity> routines = service.obtainRoutinesByUsername(username);
        if (routines != null) {
            ArrayList<RoutineEntity> entities = new ArrayList<>(routines);
            List<RoutineDTO> returnList = entities.stream().map((routine) -> {
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

    @GetMapping("/exercise/{routineid}")
    public ResponseEntity<?> showExercise(@PathVariable String routineid) {
        if (routineid == null || routineid.trim().isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("message", "El nombre de rutina no puede estar vacío."));
        }
        try {
            int id = Integer.parseInt(routineid);
            RoutineEntity entity = service.obtainExerciseByRoutine(id);
            if (entity != null) {
                RoutineDTO dto = new RoutineDTO();
                dto.setId(entity.getId());
                dto.setName(entity.getName());
                dto.setExercises(entity.getExercises());
                return ResponseEntity.ok().body(dto);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", "Error al buscar la rutina"));
            }
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body(Map.of("message", "El id debe ser un número"));
        }
    }

    @PostMapping("/add/{username}")
    public ResponseEntity<?> addRoutine(@PathVariable String username, @RequestBody RoutineDTO dto) {
        int option = service.addRoutine(username, dto);
        switch (option) {
            case 0:
                return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("mesagge", "Creacion correcta"));
            case 1:
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("mesagge", "La rutina ya existe"));
            case 2:
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("mesagge", "Error en el servidor"));
            default:
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("mesagge", "Error desconocido"));
        }

    }

    @PutMapping("/modify/{username}")
    public ResponseEntity<?> modifyRoutine(@PathVariable String username, String oldDTO, @RequestBody ModifyRoutineDTO dto) {
        if (username == null || username.trim().isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("message", "El nombre de usuario no puede estar vacío."));
        }
        if (dto == null) {
            return ResponseEntity.badRequest().body(Map.of("message", "La rutina no existe"));
        }

        int option = service.modifyRoutine(username, dto);
        switch (option) {
            case 0:
                return ResponseEntity.status(HttpStatus.OK).body(Map.of("mesagge", "Modificado correcto"));
            default:
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("mesagge", "Error desconocido"));
        }

    }


    @DeleteMapping("/delete/{routineid}")
    public ResponseEntity<?> deleteRoutine(@PathVariable String routineid) {
        if (routineid == null || routineid.trim().isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("message", "El nombre de rutina no puede estar vacío."));
        }

        try {
            int id = Integer.parseInt(routineid);
            int option =service.deleteRoutine(id);
            switch (option){
                case 0:
                    return ResponseEntity.ok().body(Map.of("mesagge", "Borrado correcta"));
                case 1:
                    return ResponseEntity.badRequest().body(Map.of("mesagge","La rutina no existe"));
                default:
                    return ResponseEntity.internalServerError().body(Map.of("mesagge","Error interno en el servidor"));
            }

        } catch (NumberFormatException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("mesagge", "El id debe ser un número "));
        }


    }

}
