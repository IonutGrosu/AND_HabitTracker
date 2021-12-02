package io.github.ionutgrosu.and_habittracker.DAOs;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.github.ionutgrosu.and_habittracker.models.User;

public class UserDAO {
    private static UserDAO instance;

    private DatabaseReference dbRef;

    private ArrayList<User> allUsers;

    private UserDAO() {
        dbRef = FirebaseDatabase.getInstance("https://and-habittracker-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Users");
        allUsers = new ArrayList<>();
        getCurrentDbUsers();

        //  keep the logged in user data synced locally
        DatabaseReference localRef = FirebaseDatabase.getInstance("https://and-habittracker-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Users/" + FirebaseAuth.getInstance().getUid());
        localRef.keepSynced(true);
    }

    public static UserDAO getInstance() {
        if (instance == null) {
            instance = new UserDAO();
        }
        return instance;
    }

    public LiveData<User> getLoggedInUser() {
        MutableLiveData<User> userProfile = new MutableLiveData<>();
        dbRef.child(FirebaseAuth.getInstance().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User dbUser = snapshot.getValue(User.class);
                System.out.println("logged in user: " + dbUser.getUsername());
                userProfile.setValue(dbUser);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return userProfile;
    }

    public void insert(User userToSave) {
        try {
            dbRef.child(userToSave.getUid()).setValue(userToSave);
        } catch (Exception e) {
            throw e;
        }
    }

    public User getUserWithEmail(String email) {
        getCurrentDbUsers();
        for (User user :
                allUsers) {
            if (user.getEmail().equals(email)) {
                return user;
            }
        }
        return null;
    }

    public User getUserWithUsername(String username) {
        getCurrentDbUsers();
        for (User user :
                allUsers) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    public User getUserWithUid(String uid) {
        getCurrentDbUsers();
        for (User user :
                allUsers) {
            if (user.getUid().equals(uid)) {
                return user;
            }
        }
        return null;
    }

    private void getCurrentDbUsers() {

        dbRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()){
                    allUsers.clear();
                    for (DataSnapshot snapshot: Objects.requireNonNull(task.getResult()).getChildren()) {
                        allUsers.add(snapshot.getValue(User.class));
                    }
                } else {

                }
            }
        });
    }

    public void updateUser(User user) {
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    if (ds.getValue(User.class).getUid().equals(user.getUid())){
                        ds.getRef().setValue(user);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
