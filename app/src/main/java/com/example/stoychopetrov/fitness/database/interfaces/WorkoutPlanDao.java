package com.example.stoychopetrov.fitness.database.interfaces;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.stoychopetrov.fitness.database.WorkoutPlan;

/**
 * Created by Stoycho Petrov on 28.3.2018 г..
 */

@Dao
public interface WorkoutPlanDao {

    @Insert
    void insert(WorkoutPlan userRepoJoin);

}
