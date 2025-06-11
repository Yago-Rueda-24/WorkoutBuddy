package com.YagoRueda.WorkoutBuddy.service.userServiceTest;

import com.YagoRueda.WorkoutBuddy.DTO.SignupDTO;
import com.YagoRueda.WorkoutBuddy.Service.UserService;
import com.YagoRueda.WorkoutBuddy.entity.UserEntity;
import com.YagoRueda.WorkoutBuddy.repository.PetPasswordRepository;
import com.YagoRueda.WorkoutBuddy.repository.UserRepository;
import jakarta.persistence.Entity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class signUpTest {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PetPasswordRepository petPasswordRepository;

    private SignupDTO signup;


    @AfterEach
    void borrarUser() {
        if (userRepository.existsByUsername("userTest")) {
            UserEntity user = userRepository.findByUsername("userTest");
            userRepository.delete(user);
        }
    }

    @BeforeEach
    void crearUser() {
        signup = new SignupDTO();
        signup.setUsername("userTest");
        signup.setEmail("yago.rc.04@gmail.com");
        signup.setPassword("123");
        signup.setPasswordrepeat("123");
    }


    @Test
    void signUpCorrecto() {
        int ret = userService.signUp(signup);
        assertEquals(0, ret);

    }

    @Test
    void signUpUserExist() {
        signup.setUsername("user123");
        int ret = userService.signUp(signup);
        assertEquals(2, ret);

    }

    @Test
    void signUpPasswordIncorrectas() {
        signup.setPassword("123");
        signup.setPassword("1234");
        int ret = userService.signUp(signup);
        assertEquals(1, ret);

    }
}
