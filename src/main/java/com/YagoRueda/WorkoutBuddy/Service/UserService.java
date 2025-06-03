package com.YagoRueda.WorkoutBuddy.Service;

import com.YagoRueda.WorkoutBuddy.DTO.SignupDTO;
import com.YagoRueda.WorkoutBuddy.entity.UserEntity;
import com.YagoRueda.WorkoutBuddy.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@Service
public class UserService {

    private final UserRepository repository;
    private MessageDigest hash;

    public UserService(UserRepository repository) {
        this.repository = repository;
        try{
            this.hash = MessageDigest.getInstance("SHA-256");
        }catch(NoSuchAlgorithmException e){
            e.printStackTrace();
        }

    }


    public List<UserEntity> findAll() {
        return repository.findAll();
    }

    public boolean login(UserEntity user) {

        hash.reset();
        hash.update(user.getPassword().getBytes());
        byte[] hashed_password = this.hash.digest();

        UserEntity foundUser = repository.findByUsername(user.getUsername());
        if (foundUser == null) {
            return false;
        }
        return foundUser.getPassword().equals(new String(hashed_password,StandardCharsets.UTF_8));
    }

    public int signUp(SignupDTO signup) {
        boolean exists = repository.existsByUsername(signup.getUsername());
        boolean correct_password = signup.getPasswordrepeat().equals(signup.getPassword());
        if(!correct_password){
            return 1;
        }
        if (exists) {
            return 2;
        }
        hash.reset();
        hash.update(signup.getPassword().getBytes());
        byte[] hashed_password = this.hash.digest();
        //Guardar la información del usuario en base de datos
        UserEntity user = new UserEntity();
        user.setUsername(signup.getUsername());
        user.setEmail(signup.getEmail());
        user.setPassword(new String(hashed_password, StandardCharsets.UTF_8));
        repository.save(user);
        return 0;
    }


}
