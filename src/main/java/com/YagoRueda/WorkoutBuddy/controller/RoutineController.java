package com.YagoRueda.WorkoutBuddy.controller;

import com.YagoRueda.WorkoutBuddy.DTO.ModifyRoutineDTO;
import com.YagoRueda.WorkoutBuddy.DTO.RoutineDTO;
import com.YagoRueda.WorkoutBuddy.Service.RoutineService;
import com.YagoRueda.WorkoutBuddy.Service.UserService;
import com.YagoRueda.WorkoutBuddy.entity.RoutineEntity;
import jakarta.validation.Valid;
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

    /**
     * Devuelve todas las rutinas pertenecientes a un usuario
     * @param username nombre del usuario que sera usado en la busqueda
     * @return respuesta HTTP cuyo cuerpo tiene información sobre las rutinas
     */
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

    /**
     * Devuelve todos los ejercicios pertenecientes a una rutina
     * @param routineid id de la rutina que se usara en la busqueda
     * @return respuesta HTTP cuyo cuerpo tiene información sobre los ejercicios
     */
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

    /**
     * Añade una nueva rutina a la lista de rutinas de un usuario. En el cuerpo se puede pasar información de la rutina y además se puede pasar información sobre
     * los ejercicios de esta
     * @param username nombre de usuario al que se añadira la rutina
     * @param dto DTO con información de la rutina y de los ejercicios dentro de esta
     * @return Respuesta HTTP que indica si el añadido es exitoso. Devuelve OK en caso exitoso ,BAD_REQUEST y INTERNAL_SERVER_ERROR en caso de fallo
     */
    @PostMapping("/add/{username}")
    public ResponseEntity<?> addRoutine(@PathVariable String username, @RequestBody @Valid RoutineDTO dto) {
        int option = service.addRoutine(username, dto);
        switch (option) {
            case 0:
                return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("mesagge", "Creacion correcta"));
            case 1:
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("mesagge", "El usuario no existe"));
            case 2:
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("mesagge", "La lista esta vacia"));
            default:
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("mesagge", "Error desconocido"));
        }

    }

    /**
     * Modifica los datos de una rutina existente. En el cuerpo se puede pasar información de la rutina y además se puede pasar información sobre
     * los ejercicios de esta
     * @param routineid ID de la rutina a modificar
     * @param dto DTO con información de la rutina y de los ejercicios dentro de esta
     * @return Respuesta HTTP que indica si la modificación es exitosa. Devuelve OK en caso exitoso ,BAD_REQUEST y INTERNAL_SERVER_ERROR en caso de fallo
     */
    @PutMapping("/modify/{routineid}")
    public ResponseEntity<?> modifyRoutine(@PathVariable String routineid, @RequestBody @Valid RoutineDTO dto) {
        if (routineid == null || routineid.trim().isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("message", "El id de la rutina no puede estar vacío."));
        }
        if (dto == null) {
            return ResponseEntity.badRequest().body(Map.of("message", "La rutina no puede ser vacia"));
        }
        try {
            int id = Integer.parseInt(routineid);
            int option = service.modifyRoutine(id,dto);
            switch (option) {
                case 0:
                    return ResponseEntity.ok().body(Map.of("mesagge", "Actualización correcta "));
                case 1:
                    return ResponseEntity.badRequest().body(Map.of("mesagge", "La rutina no existe"));
                default:
                    return ResponseEntity.internalServerError().body(Map.of("mesagge", "Error interno en el servidor"));
            }

        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body(Map.of("message", "El id debe ser un número"));
        }

    }

    /**
     * Elimina los datos de una rutina existente. En el cuerpo se puede pasar información de la rutina y además se puede pasar información sobre
     * los ejercicios de esta
     * @param routineid ID de la rutina a modificar
     * @return Respuesta HTTP que indica si la eliminación es exitosa. Devuelve OK en caso exitoso ,BAD_REQUEST y INTERNAL_SERVER_ERROR en caso de fallo
     */
    @DeleteMapping("/delete/{routineid}")
    public ResponseEntity<?> deleteRoutine(@PathVariable String routineid) {
        if (routineid == null || routineid.trim().isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("message", "El nombre de rutina no puede estar vacío."));
        }

        try {
            int id = Integer.parseInt(routineid);
            int option = service.deleteRoutine(id);
            switch (option) {
                case 0:
                    return ResponseEntity.ok().body(Map.of("mesagge", "Borrado correcta"));
                case 1:
                    return ResponseEntity.badRequest().body(Map.of("mesagge", "La rutina no existe"));
                default:
                    return ResponseEntity.internalServerError().body(Map.of("mesagge", "Error interno en el servidor"));
            }

        } catch (NumberFormatException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("mesagge", "El id debe ser un número "));
        }


    }

}
