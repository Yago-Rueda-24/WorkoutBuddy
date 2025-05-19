package com.YagoRueda.WorkoutBuddy.repository;

import com.YagoRueda.WorkoutBuddy.entity.RoutineEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoutineRepository extends JpaRepository<RoutineEntity,Long> {

    RoutineEntity findById(long id);

    List<RoutineEntity> findByUserUsername(String name);

    boolean existsByName(String name);
}
