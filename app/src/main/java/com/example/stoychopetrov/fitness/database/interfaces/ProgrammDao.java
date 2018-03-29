package com.example.stoychopetrov.fitness.database.interfaces;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.stoychopetrov.fitness.database.Day;
import com.example.stoychopetrov.fitness.database.Programm;

import java.util.List;

/**
 * Created by Stoycho Petrov on 28.3.2018 г..
 */

@Dao
public interface ProgrammDao {
    @Query("SELECT * FROM programm")
    List<Programm> getAllProgramms();

    @Insert
    void insertAll(Programm... programms);

    @Delete
    void delete(Programm programm);
}
