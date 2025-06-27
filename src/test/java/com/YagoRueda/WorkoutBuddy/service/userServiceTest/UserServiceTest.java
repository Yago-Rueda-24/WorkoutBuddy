package com.YagoRueda.WorkoutBuddy.service.userServiceTest;

import com.YagoRueda.WorkoutBuddy.DTO.RecoverPasswordDTO;
import com.YagoRueda.WorkoutBuddy.Service.UserService;
import com.YagoRueda.WorkoutBuddy.entity.PetPasswordEntity;
import com.YagoRueda.WorkoutBuddy.entity.UserEntity;
import com.YagoRueda.WorkoutBuddy.repository.PetPasswordRepository;
import com.YagoRueda.WorkoutBuddy.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PetPasswordRepository petPasswordRepository;


    @AfterEach
    void borrarUserPruebas(){
        UserEntity u = userRepository.findByUsername("usuarioTest");
        if(u!= null){
            userRepository.delete(u);
        }
    }

    @Test
    void crearPeticionDeRecuperacion() {
        // 1. Recuperar el usuario (asegúrate de que existe en BD)
        UserEntity user = userRepository.findByUsername("bbbb");
        assertNotNull(user, "El usuario debe existir para este test");

        // 2. Crear la petición
        PetPasswordEntity peticion = new PetPasswordEntity();
        peticion.setToken(UUID.randomUUID().toString());
        peticion.setUser(user);
        peticion.setUtilizado(false);
        peticion.setExpirado(false);
        peticion.setPetition_date(Instant.now());

        // 3. Guardar en la base de datos
        PetPasswordEntity guardada = petPasswordRepository.save(peticion);

        // 4. Verificación
        assertNotNull(guardada.getId(), "La petición debe guardarse con un ID generado");
    }

    @Test
    void cambiarContrasena() throws Exception {
        // Crear usuario
        UserEntity user = new UserEntity();
        user.setUsername("usuarioTest");
        user.setPassword("antiguaPassword");
        user.setEmail("yago.rc.04@gmail.com");
        user = userRepository.save(user);

        // Crear petición de recuperación
        String token = UUID.randomUUID().toString();
        PetPasswordEntity peticion = new PetPasswordEntity();
        peticion.setToken(token);
        peticion.setUser(user);
        peticion.setUtilizado(false);
        peticion.setExpirado(false);
        peticion.setPetition_date(Instant.now());
        petPasswordRepository.save(peticion);

        // Crear DTO con nueva contraseña
        RecoverPasswordDTO dto = new RecoverPasswordDTO();
        dto.setUsername(user.getUsername());
        dto.setToken(token);
        dto.setPassword("nuevaPassword123");
        dto.setRepeatpassword("nuevaPassword123");

        // Ejecutar el cambio de contraseña
        userService.changePassword(dto);

        // Verificar que la contraseña ha sido actualizada (hasheada)
        UserEntity actualizado = userRepository.findByUsername("usuarioTest");
        MessageDigest hash = MessageDigest.getInstance("SHA-256");
        hash.reset();
        hash.update("nuevaPassword123".getBytes());
        String esperada = new String(hash.digest(), StandardCharsets.UTF_8);

        Assertions.assertEquals(esperada, actualizado.getPassword(), "La contraseña debe haber sido actualizada");

        // Verificar que la petición fue marcada como utilizada
        PetPasswordEntity peticionActualizada = petPasswordRepository.findById(peticion.getId()).orElseThrow();
        Assertions.assertTrue(peticionActualizada.getUtilizado(), "La petición debe estar marcada como utilizada");
    }

    @Test
    void tokenExpirado() throws Exception {
        // Crear usuario
        UserEntity user = new UserEntity();
        user.setUsername("usuarioTest");
        user.setPassword("antiguaPassword");
        user.setEmail("yago.rc.04@gmail.com");
        user = userRepository.save(user);




        // Crear petición de recuperación
        String token = UUID.randomUUID().toString();
        PetPasswordEntity peticion = new PetPasswordEntity();
        peticion.setToken(token);
        peticion.setUser(user);
        peticion.setUtilizado(false);
        peticion.setExpirado(false);
        peticion.setPetition_date(Instant.now().minus(Duration.ofMinutes(11)));

        petPasswordRepository.save(peticion);

        // Crear DTO con nueva contraseña
        RecoverPasswordDTO dto = new RecoverPasswordDTO();
        dto.setUsername(user.getUsername());
        dto.setToken(token);
        dto.setPassword("nuevaPassword123");
        dto.setRepeatpassword("nuevaPassword123");

        // Ejecutar el cambio de contraseña
        try {
            userService.changePassword(dto);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }finally {
            // Verificar que la petición fue marcada como utilizada
            PetPasswordEntity peticionActualizada = petPasswordRepository.findById(peticion.getId()).orElseThrow();
            Assertions.assertTrue(peticionActualizada.getExpirado(), "La petición debe estar marcada como expirada");
        }


    }
}

