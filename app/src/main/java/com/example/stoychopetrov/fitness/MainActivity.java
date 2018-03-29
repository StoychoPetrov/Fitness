package com.example.stoychopetrov.fitness;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.stoychopetrov.fitness.database.Day;
import com.example.stoychopetrov.fitness.database.Exercise;
import com.example.stoychopetrov.fitness.database.FitnessProgrammDatabase;
import com.example.stoychopetrov.fitness.database.Programm;
import com.example.stoychopetrov.fitness.database.WorkoutPlan;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    private ListView                        mProgrammsListView;
    private List<Programm>                  mProgramsList = new ArrayList<>();
    private FitnessProgrammDatabase         mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUI();
        mDatabase = FitnessProgrammDatabase.getAppDatabase(this);

        if(mDatabase.getDayDao().getAllDays().size() == 0)
            addDaysOfWeek();
        if(mDatabase.getExerciseDao().getAllExercises().size() == 0)
            addExercises();
        if(mDatabase.getProgrammDao().getAllProgramms().size() == 0)
            addProgramms();
        if(mDatabase.getWorkoutPlanDao().getAllWorkoutPlans().size() == 0)
            addPlans();

        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        setAdapter();
    }

    private void initUI(){
        mProgrammsListView  = findViewById(R.id.programms);
        mProgrammsListView.setOnItemClickListener(this);
    }

    private void setAdapter(){
        ArrayList<String> titles = new ArrayList<>();
        mProgramsList = mDatabase.getProgrammDao().getAllProgramms();

        for(Programm programm : mProgramsList){
            titles.add(programm.getProgrammTitle());
        }

        ArrayAdapter<String> programmsAdapter = new ArrayAdapter<String>(this, R.layout.item_layout, R.id.name, titles);
        mProgrammsListView.setAdapter(programmsAdapter);
    }

    private void addDaysOfWeek(){
        Day monday = new Day("Monday");
        Day thuesday = new Day("Tuesday");
        Day wednesday = new Day("Wednesday");
        Day thursday = new Day("Thursday");
        Day friday = new Day("Friday");

        mDatabase.getDayDao().insertAll(monday, thuesday, wednesday,thursday, friday);
    }

    private void addExercises(){
        Exercise exercise1 = new Exercise("Push-up", 10);
        Exercise exercise2 = new Exercise("Contralateral Limb Raises", 11);
        Exercise exercise3 = new Exercise("Bent Knee Push-up", 12);
        Exercise exercise4 = new Exercise("Push-up down", 13);
        Exercise exercise5 = new Exercise("Downward-facing Dog", 14);
        Exercise exercise6 = new Exercise(". Bent-Knee Sit-up / Crunches", 15);


        mDatabase.getExerciseDao().insertAll(exercise1, exercise2, exercise3, exercise4, exercise5, exercise6);
    }

    private void addProgramms(){
        Programm programm   = new Programm("Abs workout");
        Programm programm1  = new Programm("Anywhere workout");
        Programm programm2  = new Programm("Arms workout");

        mDatabase.getProgrammDao().insertAll(programm, programm1, programm2);
    }

    private void addPlans(){
        WorkoutPlan plan    = new WorkoutPlan(1,1,1);
        WorkoutPlan plan2   = new WorkoutPlan(1,2,1);
        WorkoutPlan plan3   = new WorkoutPlan(1,3,1);

        mDatabase.getWorkoutPlanDao().insert(plan, plan2, plan3);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Programm programm = mProgramsList.get(position);
        Intent intent = new Intent(this, ExercisesActivity.class);
        intent.putExtra("programId", programm.getProgrammId());
        startActivity(intent);
    }
}
