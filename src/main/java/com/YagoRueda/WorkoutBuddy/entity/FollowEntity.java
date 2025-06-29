package com.YagoRueda.WorkoutBuddy.entity;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
public class FollowEntity {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "follower_id", nullable=false)
    private UserEntity follower;

    @ManyToOne
    @JoinColumn(name = "followed_id", nullable=false)
    private UserEntity followed;

    private Instant followedAt = Instant.now();


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getFollowedAt() {
        return followedAt;
    }

    public void setFollowedAt(Instant followedAt) {
        this.followedAt = followedAt;
    }

    public UserEntity getFollower() {
        return follower;
    }

    public void setFollower(UserEntity follower) {
        this.follower = follower;
    }

    public UserEntity getFollowed() {
        return followed;
    }

    public void setFollowed(UserEntity followed) {
        this.followed = followed;
    }
}
