package io.github.ionutgrosu.and_habittracker.DAOs;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import io.github.ionutgrosu.and_habittracker.models.Habit;

public class HabitDAO {

    private MutableLiveData<List<Habit>> allHabits;
    private static HabitDAO instance;

    private HabitDAO(){
        allHabits = new MutableLiveData<>();
        List<Habit> temp = new ArrayList<>();
        temp.add(new Habit("test1", null, 53));
        temp.add(new Habit("test1", null, 53));
        temp.add(new Habit("test1", null, 53));
        allHabits.setValue(temp);
        Log.d("habitslist", temp.toString());
    }

    public static HabitDAO getInstance(){
        if (instance == null){
            instance = new HabitDAO();
        }
        return instance;
    }

    public LiveData<List<Habit>> getAllHabits(){
        return allHabits;
    }

    public void insert(Habit habit){
        List<Habit> currentHabits = allHabits.getValue();
        currentHabits.add(habit);
        allHabits.setValue(currentHabits);
        Log.d("wtf",habit.toString());
    }

    public void delete(String name){
        List<Habit> currenHabits = allHabits.getValue();
        for (Habit habit : currenHabits) {
            if (habit.getName().equals(name)) {
                currenHabits.remove(habit);
                break;
            }
        }
    }
}
