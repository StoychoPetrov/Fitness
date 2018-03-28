package com.example.stoychopetrov.fitness.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.stoychopetrov.fitness.database.interfaces.DayDao;
import com.example.stoychopetrov.fitness.database.interfaces.ExerciseDao;
import com.example.stoychopetrov.fitness.database.interfaces.ProgrammDao;
import com.example.stoychopetrov.fitness.database.interfaces.WorkoutPlanDao;

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

    public static FitnessProgrammDatabase getAppDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(context.getApplicationContext(), FitnessProgrammDatabase.class, "fitness-database")
                            // allow queries on the main thread.
                            // Don't do this on a real app! See PersistenceBasicSample for an example.
                            .allowMainThreadQueries()
                            .build();
        }
        return INSTANCE;
    }

}
