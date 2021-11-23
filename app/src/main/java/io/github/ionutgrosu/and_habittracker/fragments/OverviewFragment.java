package io.github.ionutgrosu.and_habittracker.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import io.github.ionutgrosu.and_habittracker.models.Habit;
import io.github.ionutgrosu.and_habittracker.viewModels.HabitAdapter;
import io.github.ionutgrosu.and_habittracker.viewModels.HabitViewModel;
import io.github.ionutgrosu.and_habittracker.activities.MainActivity;
import io.github.ionutgrosu.and_habittracker.R;

public class OverviewFragment extends Fragment {

    ProgressBar progressBar;

    RecyclerView myHabitsList;
    HabitAdapter habitAdapter;

    HabitViewModel habitViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).setTopbarTitle("Overview");
        habitViewModel.getAllHabits().observe(getViewLifecycleOwner(), new Observer<List<Habit>>() {
            @Override
            public void onChanged(List<Habit> habits) {
                habitAdapter = new HabitAdapter((ArrayList<Habit>) habits);
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

        habitViewModel = new HabitViewModel();

        return view;

    }

    private void initViews(View view){
        progressBar = view.findViewById(R.id.overviewFragmentPB);

        myHabitsList = view.findViewById(R.id.rv);
        myHabitsList.hasFixedSize();
        myHabitsList.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}