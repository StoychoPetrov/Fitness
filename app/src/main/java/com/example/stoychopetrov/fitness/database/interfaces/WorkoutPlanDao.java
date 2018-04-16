package com.example.stoychopetrov.fitness.database.interfaces;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.stoychopetrov.fitness.database.DayAndExercise;
import com.example.stoychopetrov.fitness.database.WorkoutPlan;

import java.util.List;

/**
 * Created by Stoycho Petrov on 28.3.2018 г..
 */

@Dao
public interface WorkoutPlanDao {

    // Справка на фитнес програмите
    @Query("SELECT * FROM workout_plan")
    List<WorkoutPlan> getAllWorkoutPlans();

    // Въвеждане на фитнес програми
    @Insert
    void insert(WorkoutPlan... workoutPlans);

    // Справка на всички упражнения и техните дни от седмицата по дадена програма
    @Query("SELECT day.*, exercise.* FROM day " +
            "INNER JOIN workout_plan ON day.dayId = workout_plan.dayId " +
            "INNER JOIN exercise ON workout_plan.exerciseId = exercise.exerciseId " +
            "WHERE workout_plan.programmId IN (:programmId)" +
            "ORDER BY day.dayId")
    List<DayAndExercise> getAllExercisesByProgram(int[] programmId);

    // Изтриване на упражнение в даден ден.
    @Query("delete from workout_plan where dayId IN (:dayId) AND exerciseId IN (:exerciseId) AND programmId IN (:programId)")
    void deleteExerciseByDayAndProgram(int dayId, int exerciseId, int programId);

    // Справка на програма по зададена тренировка
    @Query("SELECT * FROM workout_plan where exerciseId IN (:exerciseId)")
    WorkoutPlan getWorkoutPlanByExerciseId(int exerciseId);

    // Редактиране на ден от седмицата и от коя програма е даденото упражнение
    @Query("UPDATE workout_plan SET dayId = :dayId, programmId = :programmId WHERE exerciseId = :exerciseId")
    void updateWorkoutPlan(int dayId, int exerciseId, int programmId);
}