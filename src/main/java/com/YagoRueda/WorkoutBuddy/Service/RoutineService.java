package com.YagoRueda.WorkoutBuddy.Service;

import com.YagoRueda.WorkoutBuddy.DTO.RoutineDTO;
import com.YagoRueda.WorkoutBuddy.entity.RoutineEntity;
import com.YagoRueda.WorkoutBuddy.entity.UserEntity;
import com.YagoRueda.WorkoutBuddy.repository.RoutineRepository;
import com.YagoRueda.WorkoutBuddy.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoutineService {

    private final RoutineRepository routineRepository;
    private final UserRepository userRepository;

    public RoutineService(RoutineRepository repository, UserRepository userRepository) {
        this.routineRepository = repository;
        this.userRepository = userRepository;
    }

    public List<RoutineEntity> obtainRoutinesByUsername(String username) {
        return routineRepository.findByUserUsername(username);

    }

    public int addRoutine(String username, RoutineDTO dto) {

        if(routineRepository.existsByName(dto.getName())){
            //La rutina ya existe
            return 1;
        }
        UserEntity user = userRepository.findByUsername(username);
        if (user != null) {
            RoutineEntity routine = new RoutineEntity();
            routine.setName(dto.getName());
            routine.setUser(user);
            routine.setExercises(dto.getExercises());
            routineRepository.save(routine);
            //operación correcta
            return 0;
        } else {
            //Error interno
            return 2;
        }

    }
}
