package io.github.ionutgrosu.and_habittracker.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import io.github.ionutgrosu.and_habittracker.models.Habit;
import io.github.ionutgrosu.and_habittracker.viewModels.HabitAdapter;
import io.github.ionutgrosu.and_habittracker.viewModels.HabitViewModel;
import io.github.ionutgrosu.and_habittracker.activities.MainActivity;
import io.github.ionutgrosu.and_habittracker.R;

public class OverviewFragment extends Fragment {

    RecyclerView myHabitsList;
    HabitAdapter habitAdapter;

    HabitViewModel habitViewModel;

    ArrayList<Habit> habits;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).setTopbarTitle("Overview");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_overview, container, false);

        myHabitsList = view.findViewById(R.id.rv);
        myHabitsList.hasFixedSize();
        myHabitsList.setLayoutManager(new LinearLayoutManager(getContext()));

        habitViewModel = new HabitViewModel();
        habits = (ArrayList<Habit>) habitViewModel.getAllHabits().getValue();

        habitAdapter = new HabitAdapter(habits);
        myHabitsList.setAdapter(habitAdapter);

        return view;

    }
}