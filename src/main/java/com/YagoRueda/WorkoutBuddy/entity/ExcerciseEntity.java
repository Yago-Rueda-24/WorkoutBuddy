package com.YagoRueda.WorkoutBuddy.entity;

import jakarta.persistence.*;
import jakarta.validation.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

@Entity
public class ExcerciseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne()
    @JoinColumn(name = "routine_id")
    private RoutineEntity routine;

    @NotBlank(message = "El nombre es obligatorio")
    private String name;
    @Min(value = 1, message = "El numero de repeticiones debe ser positivo")
    private int reps;
    @Min(value = 1, message = "El numero de sets debe ser positivo")
    private int sets;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getReps() {
        return reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public int getSets() {
        return sets;
    }

    public void setSets(int sets) {
        this.sets = sets;
    }
}
