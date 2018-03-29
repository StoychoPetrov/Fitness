package com.example.stoychopetrov.fitness.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Stoycho Petrov on 28.3.2018 г..
 */
@Entity(tableName = "exercise")
public class Exercise {

    @PrimaryKey(autoGenerate = true)
    private int exerciseId;

    @ColumnInfo(name = "exercise_title")
    private String exerciseTitle;

    @ColumnInfo(name = "repeating")
    private int repeating;

    public Exercise(String exerciseTitle, int repeating) {
        this.exerciseTitle = exerciseTitle;
        this.repeating = repeating;
    }

    public int getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(int mExerciseId) {
        this.exerciseId = mExerciseId;
    }

    public String getExerciseTitle() {
        return exerciseTitle;
    }

    public void setExerciseTitle(String mExerciseTitle) {
        this.exerciseTitle = mExerciseTitle;
    }

    public int getRepeating() {
        return repeating;
    }

    public void setRepeating(int mRepeating) {
        this.repeating = mRepeating;
    }
}
