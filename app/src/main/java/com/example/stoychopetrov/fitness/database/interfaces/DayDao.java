package com.example.stoychopetrov.fitness.database.interfaces;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.stoychopetrov.fitness.database.Day;

import java.util.List;

/**
 * Created by Stoycho Petrov on 28.3.2018 г..
 */

@Dao
public interface DayDao {

    @Query("SELECT * FROM day")
    List<Day> getAllDays();

    @Insert
    void insertAll(Day... days);

    @Delete
    void delete(Day day);
}
