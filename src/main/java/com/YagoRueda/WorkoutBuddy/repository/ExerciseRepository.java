package com.YagoRueda.WorkoutBuddy.repository;

import com.YagoRueda.WorkoutBuddy.entity.ExerciseEntity;
import com.YagoRueda.WorkoutBuddy.entity.RoutineEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExerciseRepository extends JpaRepository<ExerciseEntity,Long> {
    ExerciseEntity findById(long id);

    List<ExerciseEntity> findByRoutineName(String name);


    ExerciseEntity findByName(String name);
}
