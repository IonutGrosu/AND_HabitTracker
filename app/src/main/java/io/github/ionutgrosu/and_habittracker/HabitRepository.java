package io.github.ionutgrosu.and_habittracker;

import androidx.lifecycle.LiveData;

import java.util.List;

public class HabitRepository {

    private HabitDAO habitDAO;
    private static HabitRepository instance;

    public HabitRepository() {
        habitDAO = HabitDAO.getInstance();
    }

    public static HabitRepository getInstance(){
        if (instance == null){
            instance = new HabitRepository();
        }
        return instance;
    }

    public LiveData<List<Habit>> getAllHabits(){
        return habitDAO.getAllHabits();
    }

    public void insert(Habit habit){
        habitDAO.insert(habit);
    }

    public void delete(String name){
        habitDAO.delete(name);
    }
}
