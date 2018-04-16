package com.example.room.fitness;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.room.fitness.adapters.ExercisesAdapter;
import com.example.room.fitness.database.Day;
import com.example.room.fitness.database.DayAndExercise;
import com.example.room.fitness.database.Exercise;
import com.example.room.fitness.database.FitnessProgrammDatabase;
import com.example.room.fitness.database.Programm;
import com.example.room.fitness.database.WorkoutPlan;

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

        mDatabase = FitnessProgrammDatabase.getAppDatabase(this);       // получаване на инстанция към базата от данни
        mDays     = mDatabase.getDayDao().getAllDays();                 // вземане на всички дни от базата
        mPrograms = mDatabase.getProgrammDao().getAllProgramms();

        // вземане на всички упражнения по дадена програма
        mExercises.addAll(mDatabase.getWorkoutPlanDao().getAllExercisesByProgram(new int[]{getIntent().getIntExtra("programId", 0)}));

        setAdapter();
    }

    private void setAdapter(){

        // Създаване на адаптер, който да зареди списък с упражненията
        mExercisesAdapter = new ExercisesAdapter(this, mExercises, this);
        mExercisesListView.setAdapter(mExercisesAdapter);

        // Закачане на слушател, който да възникне при продължително натискане върху едно поле от списъка
        mExercisesListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                // Създаване на диалог, който пита потребителят дали е сигурен, че иска да изтрие дадена тренировка
                AlertDialog alertDialog = new AlertDialog.Builder(ExercisesActivity.this).create();
                alertDialog.setTitle("Are you sure ?");
                alertDialog.setMessage("Do you want to delete the exercise ?");
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "YES",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();

                                DayAndExercise dayAndExercise = mExercises.get(position);

                                // изтриване на тренировка от базата по програма и ден
                                mDatabase.getWorkoutPlanDao().deleteExerciseByDayAndProgram(dayAndExercise.getDay().getDayId(), dayAndExercise.getExercise().getExerciseId(), getIntent().getIntExtra("programId", 0));

                                // презареждане на списъка с упражнения
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
            AddExerciseDialog dialog = new AddExerciseDialog(this, mDays, mPrograms, this, null);
            dialog.show();
        }
    }

    @Override
    public void onEdit(int position) {
        // показване на прозорец за редактиране на ново упражнение
        AddExerciseDialog addExerciseDialog = new AddExerciseDialog(this, mDays, mPrograms, this, mExercises.get(position).getExercise());
        addExerciseDialog.show();
    }

    @Override
    public void onAdd(int dayPosition, int programPosition, String exerciseTitle) {

        int dayId = mDays.get(dayPosition).getDayId();              // ден в, който е добавено новото упражнение
        int programId = mPrograms.get(programPosition).getProgrammId(); // програма, в която е добавено новот упражнение

        Exercise exercise = new Exercise(exerciseTitle, 20);        // създаване на новото упражнение с име, което е въвел потребителя
        long[] exercisesIds = mDatabase.getExerciseDao().insertAll(exercise); // добавяне на новот упражнениеи всемане на неговото id

        WorkoutPlan workoutPlan = new WorkoutPlan(dayId, (int) exercisesIds[0], programId);
        mDatabase.getWorkoutPlanDao().insert(workoutPlan);

        // презареждане на списъка с упражнения
        mExercises.clear();
        mExercises.addAll(mDatabase.getWorkoutPlanDao().getAllExercisesByProgram(new int[]{getIntent().getIntExtra("programId", 0)}));

        mExercisesAdapter.notifyDataSetChanged();
    }

    @Override
    public void onUpdate(int dayPosition, int programPosition, Exercise exercise) {

        // Редактиране на даденото упражнение
        mDatabase.getExerciseDao().update(exercise);
        mDatabase.getWorkoutPlanDao().updateWorkoutPlan(mDays.get(dayPosition).getDayId(), exercise.getExerciseId(), mPrograms.get(programPosition).getProgrammId());

        // презареждане на списъка с упражнения
        mExercises.clear();
        mExercises.addAll(mDatabase.getWorkoutPlanDao().getAllExercisesByProgram(new int[]{getIntent().getIntExtra("programId", 0)}));

        mExercisesAdapter.notifyDataSetChanged();
    }
}
