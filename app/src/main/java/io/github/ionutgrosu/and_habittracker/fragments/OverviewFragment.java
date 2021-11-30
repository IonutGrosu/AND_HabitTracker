package io.github.ionutgrosu.and_habittracker.fragments;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import io.github.ionutgrosu.and_habittracker.models.Habit;
import io.github.ionutgrosu.and_habittracker.utils.Utils;
import io.github.ionutgrosu.and_habittracker.viewModels.HabitAdapter;
import io.github.ionutgrosu.and_habittracker.viewModels.HabitViewModel;
import io.github.ionutgrosu.and_habittracker.activities.MainActivity;
import io.github.ionutgrosu.and_habittracker.R;

public class OverviewFragment extends Fragment {

    ProgressBar progressBar;
    TextView noHabitsTextView;

    RecyclerView myHabitsList;
    HabitAdapter habitAdapter;

    HabitViewModel habitViewModel;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        habitViewModel = new ViewModelProvider(this).get(HabitViewModel.class);

        habitAdapter = new HabitAdapter(new ArrayList<Habit>());

        habitAdapter.setOnCheckboxCheckedListener((checked, habit) -> {
            if (checked){
                Date now = Calendar.getInstance().getTime();

                if (Utils.isDateBefore(habit.getLastCheckDate(), now)){
                    habit.increaseProgress();
                    habit.setLastCheckDate(Calendar.getInstance().getTime());
                    habitViewModel.update(habit);
                } else {
                    System.out.println("No se puede, patron");
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).setTopbarTitle("Overview");

        habitViewModel.getAllHabits().observe(getViewLifecycleOwner(), new Observer<List<Habit>>() {
            @Override
            public void onChanged(List<Habit> habits) {
                if (habits.isEmpty()){
                    noHabitsTextView.setVisibility(View.VISIBLE);
                } else {
                    noHabitsTextView.setVisibility(View.GONE);
                }
                habitAdapter.setHabits((ArrayList<Habit>) habits);

                myHabitsList.setAdapter(habitAdapter);
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_overview, container, false);

        initViews(view);

        return view;
    }

    private void initViews(View view) {
        progressBar = view.findViewById(R.id.overviewFragmentPB);
        noHabitsTextView = view.findViewById(R.id.noHabitsTextView);

        myHabitsList = view.findViewById(R.id.rv);
        myHabitsList.hasFixedSize();
        myHabitsList.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}