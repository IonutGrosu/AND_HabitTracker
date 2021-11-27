package io.github.ionutgrosu.and_habittracker.DAOs;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import io.github.ionutgrosu.and_habittracker.models.User;

public class FriendDAO {
    private static FriendDAO instance;

    private DatabaseReference dbRef;

    MutableLiveData<ArrayList<String>> senderUidsLiveData;
    ArrayList<String> senderUids;

    private FriendDAO(){
        updateDatabaseReference();

        senderUidsLiveData = new MutableLiveData<>();
        senderUids = new ArrayList<>();
        senderUidsLiveData.setValue(senderUids);

        addListenerForFriendRequests();
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

    private void addListenerForFriendRequests() {
        updateDatabaseReference();
        dbRef.addValueEventListener(friendRequestsListener);
        senderUidsLiveData.setValue(senderUids);
    }

    ValueEventListener friendRequestsListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            senderUids.clear();
            for (DataSnapshot ds: snapshot.getChildren()){
                senderUids.add(ds.getValue(String.class));
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    };

    public MutableLiveData<ArrayList<String>> getAllSenderUids() {
        updateDatabaseReference();
        addListenerForFriendRequests();
        return senderUidsLiveData;
    }

    private void updateDatabaseReference(){
        dbRef = FirebaseDatabase
                .getInstance("https://and-habittracker-default-rtdb.europe-west1.firebasedatabase.app/")
                .getReference("FriendRequests/" + FirebaseAuth.getInstance().getUid());
    }
}
