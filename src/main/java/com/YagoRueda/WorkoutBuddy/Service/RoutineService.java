package com.YagoRueda.WorkoutBuddy.Service;

import com.YagoRueda.WorkoutBuddy.entity.RoutineEntity;
import com.YagoRueda.WorkoutBuddy.entity.UserEntity;
import com.YagoRueda.WorkoutBuddy.repository.RoutineRepository;
import com.YagoRueda.WorkoutBuddy.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class RoutineService {

    private final RoutineRepository repository;

    public RoutineService(RoutineRepository repository) {
        this.repository = repository;
    }

    public List<RoutineEntity> obtainRoutinesByUsername(String username){
        return repository.findByUserUsername(username);

    }
}
