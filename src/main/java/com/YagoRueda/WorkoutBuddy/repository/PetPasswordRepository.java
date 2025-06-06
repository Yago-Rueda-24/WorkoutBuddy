package com.YagoRueda.WorkoutBuddy.repository;

import com.YagoRueda.WorkoutBuddy.entity.PetPasswordEntity;
import com.YagoRueda.WorkoutBuddy.entity.RoutineEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PetPasswordRepository extends JpaRepository<PetPasswordEntity, Long> {

    List<PetPasswordEntity> findByUserIdAndUtilizadoFalseAndExpiradoFalse(Long userId);

    @Query("SELECT p FROM PetPasswordEntity p WHERE p.user.username = :username AND p.token = :token")
    Optional<PetPasswordEntity> findByUsernameAndToken(@Param("username") String username, @Param("token") String token);


}
