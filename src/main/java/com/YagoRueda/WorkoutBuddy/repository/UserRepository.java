package com.YagoRueda.WorkoutBuddy.repository;

import com.YagoRueda.WorkoutBuddy.entity.UserEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<UserEntity, Long> {



    UserEntity findByUsername(String username);

    boolean existsByUsername(String username);

    List<UserEntity> findByUsernameStartingWith(String prefix, Pageable pageable);


    @Query(value = "SELECT * FROM user_entity LIMIT :limit", nativeQuery = true)
    List<UserEntity> findLimitedUsers(@Param("limit") int limit);


}
