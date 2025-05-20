package com.YagoRueda.WorkoutBuddy.DTO;

public class ModifyRoutineDTO {

    private RoutineDTO oldRoutine;
    private RoutineDTO newRoutine;

    public RoutineDTO getOldRoutine() {
        return oldRoutine;
    }

    public void setOldRoutine(RoutineDTO oldRoutine) {
        this.oldRoutine = oldRoutine;
    }

    public RoutineDTO getNewRoutine() {
        return newRoutine;
    }

    public void setNewRoutine(RoutineDTO newRoutine) {
        this.newRoutine = newRoutine;
    }
}
