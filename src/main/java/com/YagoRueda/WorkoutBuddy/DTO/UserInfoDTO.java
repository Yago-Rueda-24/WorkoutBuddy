package com.YagoRueda.WorkoutBuddy.DTO;

public class UserInfoDTO {
    private long id;
    private String username;
    private int routines;

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
}
