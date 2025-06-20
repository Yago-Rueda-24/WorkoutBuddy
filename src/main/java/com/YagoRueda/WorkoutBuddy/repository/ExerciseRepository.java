package com.YagoRueda.WorkoutBuddy.repository;

import com.YagoRueda.WorkoutBuddy.entity.ExerciseEntity;
import com.YagoRueda.WorkoutBuddy.entity.RoutineEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExerciseRepository extends JpaRepository<ExerciseEntity,Long> {
    /**
     * Encuentra un ejercicio en base a su id
     * @param id ID del ejercicio
     * @return Entidad de BD que representa al ejercicio
     */
    ExerciseEntity findById(long id);


    /**
     * Encuentra todos los ejercicios de una rutina en base al nombre de la rutina
     * @param name nombre de la rutina
     * @return lista de ejercicios de la rutina
     */
    List<ExerciseEntity> findByRoutineName(String name);


    /**
     * Encuentra un ejercicio en base a su nombre
     * @param name nombre del ejercicio
     * @return Entidad de BD que representa al ejercicio
     */
    ExerciseEntity findByName(String name);
}
