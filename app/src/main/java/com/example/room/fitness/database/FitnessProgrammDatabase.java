package com.example.room.fitness.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.room.fitness.database.interfaces.DayDao;
import com.example.room.fitness.database.interfaces.ExerciseDao;
import com.example.room.fitness.database.interfaces.ProgrammDao;
import com.example.room.fitness.database.interfaces.WorkoutPlanDao;

/**
 * Created by Stoycho Petrov on 28.3.2018 г..
 */

@Database(entities = { Day.class, Exercise.class, Programm.class, WorkoutPlan.class },
        version = 1)
public abstract class FitnessProgrammDatabase extends RoomDatabase{

    private static FitnessProgrammDatabase INSTANCE;

    public abstract DayDao          getDayDao();
    public abstract ExerciseDao     getExerciseDao();
    public abstract ProgrammDao     getProgrammDao();
    public abstract WorkoutPlanDao  getWorkoutPlanDao();

    // Ако не е създадена базата, се създава.
    public static FitnessProgrammDatabase getAppDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(context.getApplicationContext(), FitnessProgrammDatabase.class, "fitness-database")
                            .allowMainThreadQueries()
                            .build();
        }
        return INSTANCE;
    }

}
