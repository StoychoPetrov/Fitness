package com.example.stoychopetrov.fitness.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.stoychopetrov.fitness.R;
import com.example.stoychopetrov.fitness.database.DayAndExercise;

import java.util.List;

/**
 * Created by stoycho.petrov on 29/03/2018.
 */

public class ExercisesAdapter extends ArrayAdapter<DayAndExercise> {

    private Context                 mContext;
    private List<DayAndExercise>    mExercises;
    private OnEditClicked           mListener;

    public interface OnEditClicked {
        void onEdit(int position);
    }

    public ExercisesAdapter(@NonNull Context context, @NonNull List<DayAndExercise> objects, OnEditClicked listener) {
        super(context, R.layout.item_layout, objects);

        mContext    = context;
        mExercises  = objects;
        mListener   = listener;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater oLayInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = oLayInflater.inflate(R.layout.item_layout, null);
        }

        TextView    exercise    = convertView.findViewById(R.id.name);
        TextView    dayOfWeek   = convertView.findViewById(R.id.day_of_week);
        ImageButton editBtn     = convertView.findViewById(R.id.edit_btn);

        if(position == 0 || mExercises.get(position).getDay().getDayId() != mExercises.get(position - 1).getDay().getDayId()) {
            dayOfWeek.setText(mExercises.get(position).getDay().getDayTitle());
            dayOfWeek.setVisibility(View.VISIBLE);
        }
        else
            dayOfWeek.setVisibility(View.GONE);

        exercise.setText(mExercises.get(position).getExercise().getExerciseTitle());

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onEdit(position);
            }
        });

        return convertView;
    }
}
