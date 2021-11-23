package io.github.ionutgrosu.and_habittracker.DAOs;

import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import io.github.ionutgrosu.and_habittracker.models.Habit;

public class HabitDAO {

    private MutableLiveData<List<Habit>> allHabits;
    private static HabitDAO instance;

    private DatabaseReference dbReference;

    private HabitDAO() {

        updateDatabaseReference();

        allHabits = new MutableLiveData<>();

        List<Habit> tempList = new ArrayList<>();
        allHabits.setValue(tempList);

        getCurrentDbHabits();
    }

    public static HabitDAO getInstance() {
        if (instance == null) {
            instance = new HabitDAO();
        }
        return instance;
    }

    public LiveData<List<Habit>> getAllHabits() {
        getCurrentDbHabits();
        return allHabits;
    }

    private void getCurrentDbHabits() {
        updateDatabaseReference();
        List<Habit> habits = allHabits.getValue();

        dbReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                habits.clear();
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Habit habit = postSnapshot.getValue(Habit.class);
                    habits.add(habit);
                }
                allHabits.setValue(habits);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("The read failed: " + error.getMessage());
            }
        });
    }

    public void insert(Habit habit) {
        updateDatabaseReference();

        DatabaseReference tempRef = dbReference.push();
        habit.setId(tempRef.getKey());


        tempRef
                //.push()
                .setValue(habit);
    }

    public void update(Habit habit) {
        updateDatabaseReference();

        dbReference.child(habit.getId()).setValue(habit);

        System.out.println("********************************************* updating habit" + habit.getId());


    }

    public void delete(String name) {
        updateDatabaseReference();

    }

    private void updateDatabaseReference(){
        dbReference = FirebaseDatabase
                .getInstance("https://and-habittracker-default-rtdb.europe-west1.firebasedatabase.app/")
                .getReference("Habits/" + FirebaseAuth.getInstance().getUid());
    }
}
