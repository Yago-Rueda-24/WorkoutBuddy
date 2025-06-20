package com.YagoRueda.WorkoutBuddy.repository;

import com.YagoRueda.WorkoutBuddy.entity.UserEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    /**
     * Busca un usuario por su nombre de usuario exacto.
     * @param username el nombre de usuario
     * @return la entidad {@link UserEntity} correspondiente, o {@code null} si no se encuentra
     */
    UserEntity findByUsername(String username);

    /**
     * Verifica si existe un usuario con el nombre de usuario proporcionado.
     * @param username el nombre de usuario
     * @return {@code true} si existe un usuario con ese nombre, de lo contrario {@code false}
     */
    boolean existsByUsername(String username);

    /**
     * Busca usuarios cuyos nombres de usuario comienzan con el prefijo especificado.
     * Soporta paginación mediante el parámetro {@link Pageable}.
     * @param prefix el prefijo del nombre de usuario
     * @param pageable información de paginación (página y tamaño)
     * @return lista de usuarios que coincidan con el prefijo
     */
    List<UserEntity> findByUsernameStartingWith(String prefix, Pageable pageable);


    /**
     * Recupera una lista limitada de usuarios.
     * @param limit número máximo de usuarios a recuperar
     * @return lista de entidades {@link UserEntity} hasta el límite especificado
     */
    @Query(value = "SELECT * FROM user_entity LIMIT :limit", nativeQuery = true)
    List<UserEntity> findLimitedUsers(@Param("limit") int limit);


}
