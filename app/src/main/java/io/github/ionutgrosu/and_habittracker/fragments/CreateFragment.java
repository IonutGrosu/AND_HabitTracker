package io.github.ionutgrosu.and_habittracker.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;
import java.util.Date;

import io.github.ionutgrosu.and_habittracker.models.Habit;
import io.github.ionutgrosu.and_habittracker.viewModels.HabitViewModel;
import io.github.ionutgrosu.and_habittracker.activities.MainActivity;
import io.github.ionutgrosu.and_habittracker.R;

public class CreateFragment extends Fragment {

    private HabitViewModel habitViewModel;

    private TextInputEditText habitNameInput;
    private TextInputEditText habitDurationInput;
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

    private void clearTextInputs() {
        habitNameInput.getText().clear();
        habitDurationInput.getText().clear();
    }

    private void initViews(View view) {
        ((MainActivity) getActivity()).setTopbarTitle("Create");

        habitNameInput = view.findViewById(R.id.habitNameInput);
        habitDurationInput = view.findViewById(R.id.habitDurationInput);
        saveHabitBtn = view.findViewById(R.id.saveHabitBtn);
    }

    public void saveHabit() {
        String name = habitNameInput.getText().toString().trim();
        Date today = Calendar.getInstance().getTime();
        int duration;
        try {
            duration = Integer.parseInt(habitDurationInput.getText().toString().trim());
        } catch (NumberFormatException e){
            habitDurationInput.setError("Enter a valid duration");
            habitDurationInput.requestFocus();
            return;
        }

        if (name.isEmpty()){
            habitNameInput.setError("Enter the name of your habit");
            habitNameInput.requestFocus();
            return;
        }

        if (duration <= 0){
            habitDurationInput.setError("Enter a valid duration");
            habitDurationInput.requestFocus();
            return;
        }

        Habit temp = new Habit(
                name,
                today,
                duration
        );
        habitViewModel.insert(temp);
        clearTextInputs();
    }
}