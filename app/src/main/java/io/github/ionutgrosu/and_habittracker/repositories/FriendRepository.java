package io.github.ionutgrosu.and_habittracker.repositories;

import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;

import io.github.ionutgrosu.and_habittracker.DAOs.FriendDAO;
import io.github.ionutgrosu.and_habittracker.models.User;

public class FriendRepository {
    private static FriendRepository instance;
    private FriendDAO friendDAO;

    private FriendRepository() {
        friendDAO = FriendDAO.getInstance();
    }

    public static FriendRepository getInstance() {
        if (instance == null){
            instance = new FriendRepository();
        }
        return instance;
    }

    public void sendFriendRequest(User friendRequestReceiver, String senderUID) {
        friendDAO.saveFriendRequest(friendRequestReceiver, senderUID);
    }

    public MutableLiveData<ArrayList<String>> getAllSenderUids() {
        return friendDAO.getAllSenderUids();
    }

    public void removeFriendRequest(User user) {
        friendDAO.removeFriendRequest(user);
    }
}
