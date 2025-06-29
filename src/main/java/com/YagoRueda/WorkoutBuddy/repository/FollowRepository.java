package com.YagoRueda.WorkoutBuddy.repository;

import com.YagoRueda.WorkoutBuddy.entity.FollowEntity;
import com.YagoRueda.WorkoutBuddy.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowRepository extends JpaRepository<FollowEntity,Long> {

    boolean existsByFollowerAndFollowed(UserEntity follower, UserEntity followed);

    FollowEntity findByFollowerAndFollowed(UserEntity follower, UserEntity followed);
}
