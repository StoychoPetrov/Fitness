package com.example.stoychopetrov.fitness.database.interfaces;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.stoychopetrov.fitness.database.Day;
import com.example.stoychopetrov.fitness.database.Exercise;

import java.util.List;

/**
 * Created by Stoycho Petrov on 28.3.2018 г..
 */

@Dao
public interface ExerciseDao {

    // Справка на всички упражнения от базата
    @Query("SELECT * FROM exercise")
    List<Exercise> getAllExercises();

    // Въвеждане на упражнения
    @Insert
    long[] insertAll(Exercise... exercises);

    // Изтриване на упражнение
    @Delete
    void delete(Exercise exercise);

    // Редактиране на упражнение
    @Update
    void update(Exercise exercise);
}
