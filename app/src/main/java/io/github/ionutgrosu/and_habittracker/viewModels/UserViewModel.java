package io.github.ionutgrosu.and_habittracker.viewModels;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

import io.github.ionutgrosu.and_habittracker.models.User;
import io.github.ionutgrosu.and_habittracker.repositories.FriendRepository;
import io.github.ionutgrosu.and_habittracker.repositories.UserRepository;

public class UserViewModel extends ViewModel {

    private UserRepository userRepository;
    private FriendRepository friendRepository;

    public MutableLiveData<ArrayList<User>> usersRequestingFriendship;
    public MutableLiveData<ArrayList<User>> friends;

    public UserViewModel() {
        userRepository = UserRepository.getInstance();
        friendRepository = FriendRepository.getInstance();

        usersRequestingFriendship = new MutableLiveData<>();
        usersRequestingFriendship.setValue(new ArrayList<User>());
        friends = new MutableLiveData<>();
        friends.setValue(new ArrayList<User>());
    }

    public LiveData<User> getLoggedInUser(){
        return userRepository.getLoggedInUser();
    }

    public void saveRegisteredUser(String username, String email, String uid) throws Exception {
        User userToSave = new User(username, email, uid);

        userRepository.saveUser(userToSave);
    }

    public void sendFriendRequest(String input) {
        User friendRequestReceiver;

        if (input.contains("@")){
            friendRequestReceiver = userRepository.getUserWithEmail(input);
        } else {
            friendRequestReceiver = userRepository.getUserWithUsername(input);
        }

        String senderUID = FirebaseAuth.getInstance().getUid();

        friendRepository.sendFriendRequest(friendRequestReceiver, senderUID);

    }

    public void getFriendRequests() {
        //  get all sender uid's
        MutableLiveData<ArrayList<String>> senderUids = friendRepository.getAllSenderUids();

        senderUids.observeForever(new Observer<ArrayList<String>>() {
            @Override
            public void onChanged(ArrayList<String> strings) {
                //  get all accounts related to those uids
                ArrayList<User> users = new ArrayList<>();
                for (String uid : strings) {
                    users.add(userRepository.getUserWithUid(uid));
                }
                usersRequestingFriendship.setValue(users);
            }
        });
    }

    public void getFriends() {
        userRepository.getLoggedInUser().observeForever(new Observer<User>() {
            @Override
            public void onChanged(User user) {
                ArrayList<String> friendsUids = (ArrayList<String>) user.getFriendsUids();
                ArrayList<User> tempFriends = new ArrayList<User>();
                for (String friendUid : friendsUids) {
                    tempFriends.add(userRepository.getUserWithUid(friendUid));
                }
                friends.setValue(tempFriends);
            }
        });
    }

    public void acceptFriendRequest(User user) {
        //  delete friend request entry from db
        friendRepository.removeFriendRequest(user);

        User loggedInUser = userRepository.getUserWithUid(FirebaseAuth.getInstance().getUid());
        loggedInUser.addFriend(user.getUid());

        userRepository.updateUser(loggedInUser);

        user.addFriend(FirebaseAuth.getInstance().getUid());
        userRepository.updateUser(user);
    }

    public void declineFriendRequest(User user) {
        //  delete friend request entry from db
        friendRepository.removeFriendRequest(user);
    }

    public void removeFriend(User user) {
        User loggedInUser = userRepository.getUserWithUid(FirebaseAuth.getInstance().getUid());
        //  remove user from my friends
        loggedInUser.removeFriend(user.getUid());
        userRepository.updateUser(loggedInUser);
        //  remove me from user's friends
        user.removeFriend(loggedInUser.getUid());
        userRepository.updateUser(user);
    }
}
