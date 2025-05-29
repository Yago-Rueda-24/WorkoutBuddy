package com.YagoRueda.WorkoutBuddy.Service;

import com.YagoRueda.WorkoutBuddy.DTO.ModifyRoutineDTO;
import com.YagoRueda.WorkoutBuddy.DTO.RoutineDTO;
import com.YagoRueda.WorkoutBuddy.entity.ExerciseEntity;
import com.YagoRueda.WorkoutBuddy.entity.RoutineEntity;
import com.YagoRueda.WorkoutBuddy.entity.UserEntity;
import com.YagoRueda.WorkoutBuddy.repository.ExerciseRepository;
import com.YagoRueda.WorkoutBuddy.repository.RoutineRepository;
import com.YagoRueda.WorkoutBuddy.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoutineService {

    private final RoutineRepository routineRepository;
    private final UserRepository userRepository;
    private final ExerciseRepository exerciseRepository;

    public RoutineService(RoutineRepository repository, UserRepository userRepository, ExerciseRepository exerciseRepository) {
        this.routineRepository = repository;
        this.userRepository = userRepository;
        this.exerciseRepository = exerciseRepository;
    }

    public List<RoutineEntity> obtainRoutinesByUsername(String username) {
        return routineRepository.findByUserUsername(username);

    }

    public RoutineEntity obtainExerciseByRoutine(int id ){
        return routineRepository.findById(id);
    }


    public int addRoutine(String username, RoutineDTO dto) {

        if( dto.getExercises() == null||dto.getExercises().isEmpty()){
         return 2;
        }
        UserEntity user = userRepository.findByUsername(username);
        if (user != null) {
            RoutineEntity routine = new RoutineEntity();
            routine.setName(dto.getName());
            routine.setUser(user);
            List<ExerciseEntity> exercises = dto.getExercises();
            for (ExerciseEntity ex : exercises) {

                ex.setRoutine(routine);

            }
            routine.setExercises(exercises);
            routineRepository.save(routine);
            //operación correcta
            return 0;
        } else {
            //Error interno
            return 1;
        }

    }

    public int modifyRoutine(String username, ModifyRoutineDTO dto){
        RoutineDTO oldRoutine = dto.getOldRoutine();
        RoutineDTO newRoutine = dto.getNewRoutine();
        if(!routineRepository.existsByName(oldRoutine.getName())){
            //La rutina no existe
            return 1;
        }
        RoutineEntity routine = routineRepository.findByName(oldRoutine.getName());
        routine.setName(newRoutine.getName());
        routine.setExercises(newRoutine.getExercises());
        routineRepository.save(routine);
        return 0;
    }

    public int deleteRoutine(long id){
        if(!routineRepository.existsById(id)){
            //La rutina no existe
            return 1;
        }
        RoutineEntity routineDelete = routineRepository.findById(id);
        routineRepository.delete(routineDelete);
        //Operación correcta
        return 0;

    }
}
