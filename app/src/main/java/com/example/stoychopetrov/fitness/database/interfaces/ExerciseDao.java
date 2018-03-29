package com.example.stoychopetrov.fitness.database.interfaces;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.stoychopetrov.fitness.database.Day;
import com.example.stoychopetrov.fitness.database.Exercise;

import java.util.List;

/**
 * Created by Stoycho Petrov on 28.3.2018 г..
 */

@Dao
public interface ExerciseDao {

    @Query("SELECT * FROM exercise")
    List<Exercise> getAllExercises();

    @Insert
    long[] insertAll(Exercise... exercises);

    @Delete
    void delete(Exercise exercise);
}
