package com.example.room.fitness.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.room.fitness.R;
import com.example.room.fitness.database.DayAndExercise;

import java.util.List;

// Адаптер, който служи за зареждане на всички упражнение по дни според избрана програма
public class ExercisesAdapter extends ArrayAdapter<DayAndExercise> {

    private Context                 mContext;
    private List<DayAndExercise>    mExercises;     // лист с упражнения и ден от седмицата
    private OnEditClicked           mListener;      //

    // слушател за избиране на бутона за редактиране от даден ред от списъка с упражнения
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
            convertView = oLayInflater.inflate(R.layout.item_layout, null);    // зарежда изгледа на дадено поле от списъка
        }

        // вземане на инстанция на обектите, които ще показват данните за упражнението, по идентификационен номер
        TextView    exercise    = convertView.findViewById(R.id.name);
        TextView    dayOfWeek   = convertView.findViewById(R.id.day_of_week);
        ImageButton editBtn     = convertView.findViewById(R.id.edit_btn);

        // Ако позицията на клетката, която се зарежда, е 0 или денят на текущата тренировка е различен от деня на предната тренировка
        // се показва денят от седмицата над полето с данните за тренировката
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
                // извиква се слушателят при натиснат бутон за редактиране
                mListener.onEdit(position);
            }
        });

        return convertView;
    }
}
