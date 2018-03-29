package com.example.stoychopetrov.fitness.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;

/**
 * Created by Stoycho Petrov on 28.3.2018 г..
 */

@Entity(tableName = "workout_plan",
        primaryKeys = { "exerciseId", "programmId", "dayId" },
        foreignKeys = {
                @ForeignKey(entity = Exercise.class,
                        parentColumns = "exerciseId",
                        childColumns = "exerciseId"),
                @ForeignKey(entity = Programm.class,
                        parentColumns = "programmId",
                        childColumns = "programmId"),
                @ForeignKey(entity = Day.class,
                        parentColumns = "dayId",
                        childColumns = "dayId")
        })
public class WorkoutPlan {

    public final int dayId;
    public final int exerciseId;
    public final int programmId;

    public WorkoutPlan(int dayId, int exerciseId, int programmId) {
        this.dayId = dayId;
        this.exerciseId = exerciseId;
        this.programmId = programmId;
    }
}
