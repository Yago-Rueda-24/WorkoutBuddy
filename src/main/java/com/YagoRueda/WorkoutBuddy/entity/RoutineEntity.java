package com.YagoRueda.WorkoutBuddy.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

@Entity
public class RoutineEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotBlank(message = "El nombre es obligatorio")
    private String name;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @OneToMany(mappedBy = "routine",cascade = CascadeType.ALL)
    private List<ExcerciseEntity> exercises;
}
