package com.YagoRueda.WorkoutBuddy.controller;

import com.YagoRueda.WorkoutBuddy.DTO.UserInfoDTO;
import com.YagoRueda.WorkoutBuddy.Service.UserService;
import com.YagoRueda.WorkoutBuddy.entity.UserEntity;
import com.YagoRueda.WorkoutBuddy.exception.InpuDataException;
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

    /**
     * Endpoint para recuperar una lista de usuarios. Su longitud está limitada por un parametro en el servicio
     *
     * @param exclude Nombre de usuario que se excluira en la lista. Debe ser el usuario que efectuo la busqueda
     * @return La lista de usuarios
     */
    @GetMapping("/users")
    public ResponseEntity<?> listUsers(@RequestParam String exclude) {

        List<UserInfoDTO> users = userservice.listLimitedUsers(exclude);
        if (users != null) {
            return ResponseEntity.status(HttpStatus.OK).body(users);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", "Error interno"));
    }

    /**
     * Endpoint para recuperar una lista de usuarios, utilizando un filtro para el nombre de los usuarios buscados.
     * Su longitud está limitada por un parametro en el servicio
     *
     * @param filteredname Filtro utilizado para filtrar los usuarios quese añadiran a la lista
     * @param exclude      Nombre de usuario que se excluira en la lista. Debe ser el usuario que efectuo la busqueda
     * @return La lista de usuarios filtrada
     */
    @GetMapping("/users/{filteredname}")
    public ResponseEntity<?> listUsers(@PathVariable String filteredname, @RequestParam String exclude) {

        List<UserInfoDTO> users = userservice.listFilteredLimitedUsers(filteredname, exclude);
        if (users != null) {
            return ResponseEntity.status(HttpStatus.OK).body(users);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", "Error interno"));
    }

    @GetMapping("/info")
    public ResponseEntity<?> GetUserInfo(@RequestParam String follower, @RequestParam String followed) {

        try {
            UserInfoDTO dto = userservice.GetUserInfo(follower,followed);
            return ResponseEntity.status(HttpStatus.OK).body(dto);
        } catch (InpuDataException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", e.getMessage()));
        }

    }

    @PostMapping("/follow")
    public ResponseEntity<?> follow(@RequestParam String follower, @RequestParam String followed){

        try{
            userservice.follow(follower,followed);
            return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "Operacion correcta"));
        }catch (InpuDataException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", e.getMessage()));
        }


    }
    @PostMapping("/unfollow")
    public ResponseEntity<?> unfollow(@RequestParam String follower,@RequestParam String followed){
        try{
            userservice.unfollow(follower,followed);
            return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "Operacion correcta"));
        }catch (InpuDataException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", e.getMessage()));
        }
    }
}
