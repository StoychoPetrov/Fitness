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

public class ExercisesActivity extends AppCompatActivity implements View.OnClickListener {

    private ListView                mExercisesListView;
    private ImageView               mAddImg;

    private List<DayAndExercise>    mExercises      = new ArrayList<>();
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
        mExercises.addAll(mDatabase.getWorkoutPlanDao().getAllExercisesByProgram(new int[]{getIntent().getIntExtra("programId", 0)}));

        setAdapter();
    }

    private void setAdapter(){
        mExercisesAdapter = new ExercisesAdapter(this, mExercises);
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

        final List<Day> days              = mDatabase.getDayDao().getAllDays();
        final List<Programm> programms    = mDatabase.getProgrammDao().getAllProgramms();

        List<String> daysTitles = new ArrayList<>();
        List<String> programs   = new ArrayList<>();
        for (Day day : days)
            daysTitles.add(day.getDayTitle());

        for(Programm programm : programms)
            programs.add(programm.getProgrammTitle());

        if(v.getId() == mAddImg.getId()){
            AddExerciseDialog dialog = new AddExerciseDialog(this, daysTitles, programs, new AddExerciseDialog.OnButtonClicked() {
                @Override
                public void onAdd(int dayPosition, int programPosition, String exerciseName) {
                    int dayId = days.get(dayPosition).getDayId();
                    int programId = programms.get(programPosition).getProgrammId();

                    Exercise exercise = new Exercise(exerciseName, 20);
                    long[] exercisesIds = mDatabase.getExerciseDao().insertAll(exercise);

                    WorkoutPlan workoutPlan = new WorkoutPlan(dayId, (int) exercisesIds[0], programId);
                    mDatabase.getWorkoutPlanDao().insert(workoutPlan);

                    mExercises.clear();
                    mExercises.addAll(mDatabase.getWorkoutPlanDao().getAllExercisesByProgram(new int[]{getIntent().getIntExtra("programId", 0)}));

                    mExercisesAdapter.notifyDataSetChanged();
                }
            });

            dialog.show();
        }
    }
}
