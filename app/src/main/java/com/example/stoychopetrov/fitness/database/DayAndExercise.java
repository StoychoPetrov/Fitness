package com.example.stoychopetrov.fitness.database;

import android.arch.persistence.room.Embedded;

/**
 * Created by stoycho.petrov on 29/03/2018.
 */

public class DayAndExercise {

    @Embedded
    private Day day;

    @Embedded
    private Exercise exercise;

    public Day getDay() {
        return day;
    }

    public void setDay(Day day) {
        this.day = day;
    }

    public Exercise getExercise() {
        return exercise;
    }

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }
}
