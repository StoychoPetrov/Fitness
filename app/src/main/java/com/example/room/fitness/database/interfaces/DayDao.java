package com.example.room.fitness.database.interfaces;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.room.fitness.database.Day;

import java.util.List;

/**
 * Created by Stoycho Petrov on 28.3.2018 г..
 */

@Dao
public interface DayDao {

    // Справка на всички дни от базата.
    @Query("SELECT * FROM day")
    List<Day> getAllDays();

    //Въвеждане на дни в базата
    @Insert
    void insertAll(Day... days);

    // Изтриване на дни от базата
    @Delete
    void deleteAll(Day... days);

    // Изтриване на ден от базата
    @Delete
    void delete(Day day);
}
