package io.github.ionutgrosu.and_habittracker;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class HabitViewModel extends ViewModel {

    private HabitRepository repository;

    public HabitViewModel() {
        repository = HabitRepository.getInstance();
    }

    public LiveData<List<Habit>> getAllHabits(){
        return repository.getAllHabits();
    }

    public void insert(Habit habit){
        repository.insert(habit);
    }

    public void delete(String name){
        repository.delete(name);
    }
}
