package io.github.ionutgrosu.and_habittracker.DAOs;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.github.ionutgrosu.and_habittracker.models.User;

public class UserDAO {
    private static UserDAO instance;

    private FirebaseDatabase db;

    private UserDAO(){
        db = FirebaseDatabase.getInstance("https://and-habittracker-default-rtdb.europe-west1.firebasedatabase.app/");
    }

    public static UserDAO getInstance(){
        if (instance == null){
            instance = new UserDAO();
        }
        return instance;
    }

    public LiveData<User> getLoggedInUser(){
        FirebaseUser authUser = FirebaseAuth.getInstance().getCurrentUser();
        MutableLiveData<User> userProfile = new MutableLiveData<>();
        db.getReference("Users").child(authUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User dbUser = snapshot.getValue(User.class);
                System.out.println(dbUser.getUsername());
                userProfile.setValue(dbUser);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return userProfile;
    }
}
