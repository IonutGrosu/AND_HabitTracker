package io.github.ionutgrosu.and_habittracker.DAOs;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.github.ionutgrosu.and_habittracker.models.User;

public class UserDAO {
    private static UserDAO instance;

    private DatabaseReference dbRef;

    private UserDAO(){
        dbRef = FirebaseDatabase.getInstance("https://and-habittracker-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Users");
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
        dbRef.child(authUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
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

    public void insert(User userToSave) {
        try{
            dbRef.child(userToSave.getUid()).setValue(userToSave);
        } catch (Exception e){
            throw e;
        }
    }
}
