package com.example.room.fitness.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;

/**
 * Created by Stoycho Petrov on 28.3.2018 г..
 */

// Създаване на връзките между таблиците. Таблицата "workout_plan обединява останалите таблици".
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

    private final int dayId;
    private final int exerciseId;
    private final int programmId;

    public WorkoutPlan(int dayId, int exerciseId, int programmId) {
        this.dayId = dayId;
        this.exerciseId = exerciseId;
        this.programmId = programmId;
    }

    public int getDayId() {
        return dayId;
    }

    public int getExerciseId() {
        return exerciseId;
    }

    public int getProgrammId() {
        return programmId;
    }
}
