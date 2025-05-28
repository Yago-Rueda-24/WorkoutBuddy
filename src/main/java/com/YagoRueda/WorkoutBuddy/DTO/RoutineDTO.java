package com.YagoRueda.WorkoutBuddy.DTO;

import com.YagoRueda.WorkoutBuddy.entity.ExerciseEntity;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public class RoutineDTO {

    private long id;
    @NotBlank(message = "El nombre de la rutina no puede estar vacío")
    private String name;
    @Valid
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
