package com.YagoRueda.WorkoutBuddy.repository;

import com.YagoRueda.WorkoutBuddy.entity.RoutineEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoutineRepository extends JpaRepository<RoutineEntity,Long> {

    /**
     * Busca una rutina por su identificador único.
     * @param id el ID de la rutina
     * @return la entidad de rutina correspondiente al ID, o {@code null} si no se encuentra
     */
    RoutineEntity findById(long id);

    /**
     * Recupera una lista de rutinas asociadas a un usuario por su nombre de usuario.
     * @param name el nombre de usuario
     * @return lista de rutinas pertenecientes al usuario
     */
    List<RoutineEntity> findByUserUsername(String name);

    /**
     * Verifica si existe una rutina con un nombre específico.
     * @param name el nombre de la rutina
     * @return {@code true} si existe una rutina con ese nombre, de lo contrario {@code false}
     */
    boolean existsByName(String name);

    /**
     * Busca una rutina por su nombre exacto.
     *
     * @param name el nombre de la rutina
     * @return la entidad de rutina correspondiente al nombre, o {@code null} si no se encuentra
     */
    RoutineEntity findByName(String name);
}
