package com.YagoRueda.WorkoutBuddy.DTO;

import com.YagoRueda.WorkoutBuddy.entity.ExerciseEntity;

import java.util.List;

public class RoutineDTO {

    private long id;
    private String name;
    private List<ExerciseEntity> exercises;

    public List<ExerciseEntity> getExercises() {
        return exercises;
    }

    public void setExercises(List<ExerciseEntity> exercises) {
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
