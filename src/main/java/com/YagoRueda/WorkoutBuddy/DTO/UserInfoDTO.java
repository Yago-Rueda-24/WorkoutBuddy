package com.YagoRueda.WorkoutBuddy.DTO;

public class UserInfoDTO {
    private long id;
    private String username;
    private int routines;
    private boolean following;
    private long followers;
    private long followed;


    public int getRoutines() {return routines;}

    public void setRoutines(int routines) {this.routines = routines;}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isFollowing() { return following; }

    public void setFollowing(boolean following) { this.following = following; }

    public long getFollowers() { return followers; }

    public void setFollowers(long followers) { this.followers = followers; }

    public long getFollowed() { return followed; }

    public void setFollowed(long followed) { this.followed = followed; }
}
