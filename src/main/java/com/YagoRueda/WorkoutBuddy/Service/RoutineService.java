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

    /**
     * Servicio que obtiene todas las rutinas de un usuario
     * @param username Nombre del usuario del que se obtendran las rutinas
     * @return Lista de rutinas del usuario
     */
    public List<RoutineEntity> obtainRoutinesByUsername(String username) {
        return routineRepository.findByUserUsername(username);

    }

    /**
     * Servicio que obtiene todos los ejercicios de una rutina
     * @param id ID de la rutina de la que se obtendran los ejercicios
     * @return Lista de ejercicios
     */
    public RoutineEntity obtainExerciseByRoutine(int id) {
        return routineRepository.findById(id);
    }

    /**
     * Servicio que añade una rutina nueva a las rutinas de un usuario. La rutina también puede contener ejercicios
     * @param username Nombre de usuario propietario de la rutina
     * @param dto DTO que contiene la información de la rutina
     * @return Entero que representa el estado de la operación
     */
    public int addRoutine(String username, RoutineDTO dto) {

        if (dto.getExercises() == null || dto.getExercises().isEmpty()) {
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

    /**
     * Servicio que modifica una rutina de un usuario. La rutina también puede contener ejercicios
     * @param id ID de la rutina modificada
     * @param dto DTO que contiene la información de la rutina
     * @return Entero que representa el estado de la operación
     */
    public int modifyRoutine(long id, RoutineDTO dto) {

        if (!routineRepository.existsById(id)) {
            //La rutina no existe
            return 1;
        }
        RoutineEntity routine = routineRepository.findById(id);
        routine.getExercises().clear();

        routine.setName(dto.getName());
        List<ExerciseEntity> exercises = dto.getExercises();
        for (ExerciseEntity ex : exercises) {
            ex.setRoutine(routine);
        }
        routine.getExercises().addAll(dto.getExercises());
        routineRepository.save(routine);
        return 0;
    }

    /**
     * Servicio que elimina una rutina de un usuario.
     * @param id ID de la rutina a eliminar
     * @return Entero que representa el estado de la operación
     */
    public int deleteRoutine(long id) {
        if (!routineRepository.existsById(id)) {
            //La rutina no existe
            return 1;
        }
        RoutineEntity routineDelete = routineRepository.findById(id);
        routineRepository.delete(routineDelete);
        //Operación correcta
        return 0;

    }
}
