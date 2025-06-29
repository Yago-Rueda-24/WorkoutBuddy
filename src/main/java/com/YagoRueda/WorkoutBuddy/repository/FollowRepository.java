package com.YagoRueda.WorkoutBuddy.repository;

import com.YagoRueda.WorkoutBuddy.entity.FollowEntity;
import com.YagoRueda.WorkoutBuddy.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowRepository extends JpaRepository<FollowEntity,Long> {

    /**
     * Indica si existe follow indicando el seguido y el seguidor
     * @param follower {@link UserEntity} del usuario seguidor
     * @param followed {@link UserEntity} del usuario seguido
     * @return  {@code true} si existe {@code false} si no existe
     */
    boolean existsByFollowerAndFollowed(UserEntity follower, UserEntity followed);

    /**
     * Devuelve el seguimiento entre 2 usuario, si existe
     * @param follower {@link UserEntity} del usuario seguidor
     * @param followed {@link UserEntity} del usuario seguido
     * @return {@link FollowEntity} del seguimiento si existe, si no devuelve {@code null}
     */
    FollowEntity findByFollowerAndFollowed(UserEntity follower, UserEntity followed);

    /**
     * Devuelve el número de usuarios que están siguiendo al usuario dado
     * @param followed {@link UserEntity} del usuario del que se quiere saber la información
     * @return {@code long} con el número seguidores del usuario
     */
    long countByFollowed(UserEntity followed);

    /**
     * Devuelve cuántos usuarios está siguiendo el usuario dado.
     * @param follower {@link UserEntity} del usuario del que se quiere saber la información
     * @return {@code long} con el número seguidos del usuario
     */
    long countByFollower(UserEntity follower);


}
