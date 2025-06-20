package com.YagoRueda.WorkoutBuddy.repository;

import com.YagoRueda.WorkoutBuddy.entity.PetPasswordEntity;
import com.YagoRueda.WorkoutBuddy.entity.RoutineEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PetPasswordRepository extends JpaRepository<PetPasswordEntity, Long> {

    /**
     * Busca las peticiones de un usuario que no hayan expirado y no se hayan usado
     * @param userId ID del usuario
     * @return Lista de peticiones
     */
    List<PetPasswordEntity> findByUserIdAndUtilizadoFalseAndExpiradoFalse(Long userId);

    /**
     * Busca una petición en base al usario que la hizo y al token de la petición
     * @param username Nombre de usuario
     * @param token Token de la petición
     * @return Entidad de la petición, si existe
     */
    @Query("SELECT p FROM PetPasswordEntity p WHERE p.user.username = :username AND p.token = :token")
    Optional<PetPasswordEntity> findByUsernameAndToken(@Param("username") String username, @Param("token") String token);


}
