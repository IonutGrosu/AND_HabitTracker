package io.github.ionutgrosu.and_habittracker.DAOs;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import io.github.ionutgrosu.and_habittracker.models.User;

public class FriendDAO {
    private static FriendDAO instance;

    private DatabaseReference dbRef;

    private FriendDAO(){
        dbRef = FirebaseDatabase
                .getInstance("https://and-habittracker-default-rtdb.europe-west1.firebasedatabase.app/")
                .getReference("FriendRequests");
    }

    public static FriendDAO getInstance() {
        if (instance == null){
            instance = new FriendDAO();
        }
        return instance;
    }


    public void saveFriendRequest(User friendRequestReceiver, String senderUID) {
        dbRef.child(friendRequestReceiver.getUid()).push().setValue(senderUID);
    }
}
