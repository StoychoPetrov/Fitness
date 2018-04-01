package com.example.stoychopetrov.fitness;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.stoychopetrov.fitness.database.Day;
import com.example.stoychopetrov.fitness.database.Exercise;
import com.example.stoychopetrov.fitness.database.FitnessProgrammDatabase;
import com.example.stoychopetrov.fitness.database.Programm;
import com.example.stoychopetrov.fitness.database.WorkoutPlan;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by stoycho.petrov on 29/03/2018.
 */

public class AddExerciseDialog extends Dialog{

    private FitnessProgrammDatabase mDatabase;

    public interface OnButtonClicked {
        void onAdd(int dayPosition, int programPosition, String exerciseTitle);
        void onUpdate(int dayPosition, int programPosition, Exercise exercise);
    }

    public AddExerciseDialog(@NonNull final Context context, final List<Day> days, List<Programm> programms, final OnButtonClicked listener, final Exercise exercise) {
        super(context);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.add_exercise_dialog);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);

        final boolean isUpdate = exercise != null;

        final Spinner     daysSpinner       = findViewById(R.id.day_spinner);
        final Spinner     programmsSpinner  = findViewById(R.id.programm_spinner);
        final EditText    exerciseName      = findViewById(R.id.exercise_title);
        Button            addBtn            = findViewById(R.id.add_button);

        List<String> daysTitles = new ArrayList<>();
        for(Day day : days)
            daysTitles.add(day.getDayTitle());

        List<String> programsTitles = new ArrayList<>();
        for(Programm programm : programms)
            programsTitles.add(programm.getProgrammTitle());

        ArrayAdapter<String> adapterDays = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, daysTitles);
        daysSpinner.setAdapter(adapterDays);

        ArrayAdapter<String> adapterProgramm = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, programsTitles);
        programmsSpinner.setAdapter(adapterProgramm);

        if(isUpdate) {
            mDatabase = FitnessProgrammDatabase.getAppDatabase(context);

            WorkoutPlan workoutPlan = mDatabase.getWorkoutPlanDao().getWorkoutPlanByExerciseId(exercise.getExerciseId());

            for(int i = 0; i < days.size(); i++){
                if(days.get(i).getDayId() == workoutPlan.getDayId())
                    daysSpinner.setSelection(i);
            }

            for(int i = 0 ; i < programms.size(); i++){
                if(programms.get(i).getProgrammId() == workoutPlan.getProgrammId())
                    programmsSpinner.setSelection(i);
            }

            exerciseName.setText(exercise.getExerciseTitle());
            addBtn.setText("UPDATE");
        }

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(exerciseName.getText().toString().isEmpty())
                    Toast.makeText(context, "Please, insert exercise name", Toast.LENGTH_SHORT).show();
                else if (!isUpdate){
                    dismiss();
                    listener.onAdd(daysSpinner.getSelectedItemPosition(), programmsSpinner.getSelectedItemPosition(), exerciseName.getText().toString());
                }
                else {
                    dismiss();
                    exercise.setExerciseTitle(exerciseName.getText().toString());
                    listener.onUpdate(daysSpinner.getSelectedItemPosition(), programmsSpinner.getSelectedItemPosition(), exercise);
                }
            }
        });
    }
}
