package com.YagoRueda.WorkoutBuddy.DTO;

import com.YagoRueda.WorkoutBuddy.entity.ExcerciseEntity;
import com.YagoRueda.WorkoutBuddy.entity.UserEntity;

import java.util.List;

public class RoutineDTO {

    private long id;
    private String name;
    private List<ExcerciseEntity> exercises;

    public List<ExcerciseEntity> getExercises() {
        return exercises;
    }

    public void setExercises(List<ExcerciseEntity> exercises) {
        this.exercises = exercises;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(long id) {
        this.id = id;
    }
}
