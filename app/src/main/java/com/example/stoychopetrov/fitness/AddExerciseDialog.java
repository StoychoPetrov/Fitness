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

import java.util.List;

/**
 * Created by stoycho.petrov on 29/03/2018.
 */

public class AddExerciseDialog extends Dialog{

    public interface OnButtonClicked {
        void onAdd(int dayPosition, int programPosition, String exerciseTitle);
    }

    public AddExerciseDialog(@NonNull final Context context, final List<String> days, List<String> exercises, final OnButtonClicked listener) {
        super(context);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.add_exercise_dialog);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);


        final Spinner     daysSpinner       = findViewById(R.id.day_spinner);
        final Spinner     programmsSpinner  = findViewById(R.id.programm_spinner);
        final EditText    exerciseName      = findViewById(R.id.exercise_title);
        Button            addBtn            = findViewById(R.id.add_button);

        ArrayAdapter<String> adapterDays = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, days);
        daysSpinner.setAdapter(adapterDays);

        ArrayAdapter<String> adapterProgramm = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, exercises);
        programmsSpinner.setAdapter(adapterProgramm);


        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(exerciseName.getText().toString().isEmpty())
                    Toast.makeText(context, "Please, insert exercise name", Toast.LENGTH_SHORT).show();
                else {
                    dismiss();
                    listener.onAdd(daysSpinner.getSelectedItemPosition(), programmsSpinner.getSelectedItemPosition(), exerciseName.getText().toString());
                }
            }
        });
    }
}
