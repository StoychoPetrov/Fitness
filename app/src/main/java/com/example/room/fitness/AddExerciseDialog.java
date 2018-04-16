package com.example.room.fitness;

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

import com.example.room.fitness.database.Day;
import com.example.room.fitness.database.Exercise;
import com.example.room.fitness.database.FitnessProgrammDatabase;
import com.example.room.fitness.database.Programm;
import com.example.room.fitness.database.WorkoutPlan;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by stoycho.petrov on 29/03/2018.
 */

// Диалог за създаване на нова тренировка и редактиране на съществуваща такава.
public class AddExerciseDialog extends Dialog{

    private FitnessProgrammDatabase mDatabase;  // инстанция на базата от данни.

    // Слишатели за избрани бутони от диалога.
    public interface OnButtonClicked {
        void onAdd(int dayPosition, int programPosition, String exerciseTitle);
        void onUpdate(int dayPosition, int programPosition, Exercise exercise);
    }

    public AddExerciseDialog(@NonNull final Context context, final List<Day> days, List<Programm> programms, final OnButtonClicked listener, final Exercise exercise) {
        super(context);

        requestWindowFeature(Window.FEATURE_NO_TITLE);      // диалогът да няма свободно пространство за заглавие
        setContentView(R.layout.add_exercise_dialog);       // зарежда изгледа на диалога
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);

        final boolean isUpdate = exercise != null;          // проверява дали ще се редактира тренировка или ще се добавя нова

        final Spinner     daysSpinner       = findViewById(R.id.day_spinner);
        final Spinner     programmsSpinner  = findViewById(R.id.programm_spinner);
        final EditText    exerciseName      = findViewById(R.id.exercise_title);
        Button            addBtn            = findViewById(R.id.add_button);

        // Създава списък с имената на дните от седмицата
        List<String> daysTitles = new ArrayList<>();
        for(Day day : days)
            daysTitles.add(day.getDayTitle());

        //Създава списък с имената на програмите.
        List<String> programsTitles = new ArrayList<>();
        for(Programm programm : programms)
            programsTitles.add(programm.getProgrammTitle());

        // Зарежда падащо меню за избор на ден от седмицата
        ArrayAdapter<String> adapterDays = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, daysTitles);
        daysSpinner.setAdapter(adapterDays);

        // Зарежда падащо меню за избор на програма.
        ArrayAdapter<String> adapterProgramm = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, programsTitles);
        programmsSpinner.setAdapter(adapterProgramm);

        if(isUpdate) {
            mDatabase = FitnessProgrammDatabase.getAppDatabase(context);

            WorkoutPlan workoutPlan = mDatabase.getWorkoutPlanDao().getWorkoutPlanByExerciseId(exercise.getExerciseId());

            // при редакция, се зарежда текущия ден на тренировката в падащото меню
            for(int i = 0; i < days.size(); i++){
                if(days.get(i).getDayId() == workoutPlan.getDayId())
                    daysSpinner.setSelection(i);
            }

            // при редакция се зарежда текущата програма в падащото меню
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
                // Проверка дали потребителят е попълнил име на новата тренировка
                if(exerciseName.getText().toString().isEmpty())
                    Toast.makeText(context, "Please, insert exercise name", Toast.LENGTH_SHORT).show();
                else if (!isUpdate){    // ако се добавя нова тренировка, възниква слушателят за добавяне
                    dismiss();
                    listener.onAdd(daysSpinner.getSelectedItemPosition(), programmsSpinner.getSelectedItemPosition(), exerciseName.getText().toString());
                }
                else {                  // редакция на дадена тренировка
                    dismiss();
                    exercise.setExerciseTitle(exerciseName.getText().toString());
                    listener.onUpdate(daysSpinner.getSelectedItemPosition(), programmsSpinner.getSelectedItemPosition(), exercise);
                }
            }
        });
    }
}
