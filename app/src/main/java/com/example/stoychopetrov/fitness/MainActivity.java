package com.example.stoychopetrov.fitness;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.stoychopetrov.fitness.database.Day;
import com.example.stoychopetrov.fitness.database.FitnessProgrammDatabase;

public class MainActivity extends AppCompatActivity {

    private FitnessProgrammDatabase mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDatabase = FitnessProgrammDatabase.getAppDatabase(this);
        addDaysOfWeek();

    }

    private void addDaysOfWeek(){
        Day monday = new Day();
        monday.setDayTitle("Monday");

        Day thuesday = new Day();
        monday.setDayTitle("Tuesday");

        Day wednesday = new Day();
        monday.setDayTitle("Wednesday ");

        Day thursday = new Day();
        monday.setDayTitle("Thursday");

        Day friday = new Day();
        monday.setDayTitle("Friday");

        mDatabase.getDayDao().insertAll(monday, thuesday, wednesday,thursday, friday);
    }
}
