package com.example.stoychopetrov.fitness;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.stoychopetrov.fitness.adapters.ExercisesAdapter;
import com.example.stoychopetrov.fitness.database.Day;
import com.example.stoychopetrov.fitness.database.DayAndExercise;
import com.example.stoychopetrov.fitness.database.Exercise;
import com.example.stoychopetrov.fitness.database.FitnessProgrammDatabase;
import com.example.stoychopetrov.fitness.database.Programm;
import com.example.stoychopetrov.fitness.database.WorkoutPlan;

import java.util.ArrayList;
import java.util.List;

public class ExercisesActivity extends AppCompatActivity implements View.OnClickListener, ExercisesAdapter.OnEditClicked, AddExerciseDialog.OnButtonClicked {

    private ListView                mExercisesListView;
    private ImageView               mAddImg;

    private List<DayAndExercise>    mExercises      = new ArrayList<>();
    private List<Day>               mDays           = new ArrayList<>();
    private List<Programm>          mPrograms       = new ArrayList<>();

    private FitnessProgrammDatabase mDatabase;
    private ExercisesAdapter        mExercisesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercises);

        initUI();
    }

    private void initUI(){
        mExercisesListView  = findViewById(R.id.exercises_list_view);
        mAddImg             = findViewById(R.id.add_btn);

        mAddImg.setOnClickListener(this);
        mExercisesListView.setLongClickable(true);

        mDatabase = FitnessProgrammDatabase.getAppDatabase(this);
        mDays     = mDatabase.getDayDao().getAllDays();
        mPrograms = mDatabase.getProgrammDao().getAllProgramms();

        mExercises.addAll(mDatabase.getWorkoutPlanDao().getAllExercisesByProgram(new int[]{getIntent().getIntExtra("programId", 0)}));

        setAdapter();
    }

    private void setAdapter(){
        mExercisesAdapter = new ExercisesAdapter(this, mExercises, this);
        mExercisesListView.setAdapter(mExercisesAdapter);

        mExercisesListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog alertDialog = new AlertDialog.Builder(ExercisesActivity.this).create();
                alertDialog.setTitle("Are you sure ?");
                alertDialog.setMessage("Do you want to delete the exercise ?");
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "YES",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();

                                DayAndExercise dayAndExercise = mExercises.get(position);

                                mDatabase.getWorkoutPlanDao().deleteExerciseByDayAndProgram(dayAndExercise.getDay().getDayId(), dayAndExercise.getExercise().getExerciseId(), getIntent().getIntExtra("programId", 0));

                                mExercises.clear();
                                mExercises.addAll(mDatabase.getWorkoutPlanDao().getAllExercisesByProgram(new int[]{getIntent().getIntExtra("programId", 0)}));
                                mExercisesAdapter.notifyDataSetChanged();
                            }
                        });
                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "NO",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                alertDialog.show();

                return true;
            }
        });
    }

    @Override
    public void onClick(View v) {

        if(v.getId() == mAddImg.getId()) {
            final List<Programm> programms = mDatabase.getProgrammDao().getAllProgramms();

            List<String> daysTitles = new ArrayList<>();
            List<String> programs = new ArrayList<>();
            for (Day day : mDays)
                daysTitles.add(day.getDayTitle());

            for (Programm programm : programms)
                programs.add(programm.getProgrammTitle());

            AddExerciseDialog dialog = new AddExerciseDialog(this, mDays, mPrograms, this, null);
            dialog.show();
        }
    }

    @Override
    public void onEdit(int position) {
        List<String> daysTitles = new ArrayList<>();
        List<String> programs   = new ArrayList<>();
        for (Day day : mDays)
            daysTitles.add(day.getDayTitle());

        for (Programm programm : mPrograms)
            programs.add(programm.getProgrammTitle());

        AddExerciseDialog addExerciseDialog = new AddExerciseDialog(this, mDays, mPrograms, this, mExercises.get(position).getExercise());

        addExerciseDialog.show();
    }

    @Override
    public void onAdd(int dayPosition, int programPosition, String exerciseTitle) {
        int dayId = mDays.get(dayPosition).getDayId();
        int programId = mPrograms.get(programPosition).getProgrammId();

        Exercise exercise = new Exercise(exerciseTitle, 20);
        long[] exercisesIds = mDatabase.getExerciseDao().insertAll(exercise);

        WorkoutPlan workoutPlan = new WorkoutPlan(dayId, (int) exercisesIds[0], programId);
        mDatabase.getWorkoutPlanDao().insert(workoutPlan);

        mExercises.clear();
        mExercises.addAll(mDatabase.getWorkoutPlanDao().getAllExercisesByProgram(new int[]{getIntent().getIntExtra("programId", 0)}));

        mExercisesAdapter.notifyDataSetChanged();
    }

    @Override
    public void onUpdate(int dayPosition, int programPosition, Exercise exercise) {
        mDatabase.getExerciseDao().update(exercise);
        mDatabase.getWorkoutPlanDao().updateWorkoutPlan(mDays.get(dayPosition).getDayId(), exercise.getExerciseId(), mPrograms.get(programPosition).getProgrammId());

        mExercises.clear();
        mExercises.addAll(mDatabase.getWorkoutPlanDao().getAllExercisesByProgram(new int[]{getIntent().getIntExtra("programId", 0)}));

        mExercisesAdapter.notifyDataSetChanged();
    }
}
