package com.YagoRueda.WorkoutBuddy.repository;

import com.YagoRueda.WorkoutBuddy.entity.PetPasswordEntity;
import com.YagoRueda.WorkoutBuddy.entity.RoutineEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PetPasswordRepository extends JpaRepository<PetPasswordEntity, Long> {

    List<PetPasswordEntity> findByUserIdAndUtilizadoFalseAndExpiradoFalse(Long userId);


}
