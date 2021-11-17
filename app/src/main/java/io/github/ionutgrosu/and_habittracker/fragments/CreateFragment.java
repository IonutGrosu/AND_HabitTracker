package io.github.ionutgrosu.and_habittracker.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.Calendar;

import io.github.ionutgrosu.and_habittracker.models.Habit;
import io.github.ionutgrosu.and_habittracker.viewModels.HabitViewModel;
import io.github.ionutgrosu.and_habittracker.activities.MainActivity;
import io.github.ionutgrosu.and_habittracker.R;

public class CreateFragment extends Fragment {

    private HabitViewModel habitViewModel;

    private EditText habitNameInput;
    private EditText habitDurationInput;
    private Button saveHabitBtn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        habitViewModel = new ViewModelProvider(this).get(HabitViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create, container, false);

        initViews(view);

        saveHabitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveHabit();
            }
        });

        return view;
    }

    private void initViews(View view) {
        ((MainActivity) getActivity()).setTopbarTitle("Create");

        habitNameInput = view.findViewById(R.id.habitNameInput);
        habitDurationInput = view.findViewById(R.id.habitDurationInput);
        saveHabitBtn = view.findViewById(R.id.saveHabitBtn);
    }

    public void saveHabit(){
        Habit temp = new Habit(
                habitNameInput.getText().toString(),
                Calendar.getInstance().getTime(),
                Integer.parseInt(habitDurationInput.getText().toString())
        );
        habitViewModel.insert(temp);
    }
}