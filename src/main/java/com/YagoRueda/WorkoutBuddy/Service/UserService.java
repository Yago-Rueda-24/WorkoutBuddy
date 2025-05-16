package com.YagoRueda.WorkoutBuddy.Service;

import com.YagoRueda.WorkoutBuddy.entity.UserEntity;
import com.YagoRueda.WorkoutBuddy.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final  UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }


    public List<UserEntity> findAll() {
        return repository.findAll();
    }

    public boolean login(UserEntity user) {

        UserEntity foundUser = repository.findByUsername(user.getUsername());
        if (foundUser == null) {
            return false;
        }
        return foundUser.getPassword().equals(user.getPassword());
    }


}
