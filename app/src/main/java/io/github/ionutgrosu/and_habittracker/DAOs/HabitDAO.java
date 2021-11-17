package io.github.ionutgrosu.and_habittracker.DAOs;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import io.github.ionutgrosu.and_habittracker.models.Habit;

public class HabitDAO {

    private MutableLiveData<List<Habit>> allHabits;
    private static HabitDAO instance;

    private FirebaseDatabase db;

    private HabitDAO(){
        db = FirebaseDatabase.getInstance("https://and-habittracker-default-rtdb.europe-west1.firebasedatabase.app/");

        allHabits = new MutableLiveData<>();
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
        db.getReference("Habits")
                .setValue(habit)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                    }
                });
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
