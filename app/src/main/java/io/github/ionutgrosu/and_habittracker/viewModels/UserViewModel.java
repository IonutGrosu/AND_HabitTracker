package io.github.ionutgrosu.and_habittracker.viewModels;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;

import io.github.ionutgrosu.and_habittracker.models.User;
import io.github.ionutgrosu.and_habittracker.repositories.FriendRepository;
import io.github.ionutgrosu.and_habittracker.repositories.UserRepository;

public class UserViewModel extends ViewModel {

    private UserRepository userRepository;
    private FriendRepository friendRepository;

    public UserViewModel() {
        userRepository = UserRepository.getInstance();
        friendRepository = FriendRepository.getInstance();
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
        User friendRequestSender = getLoggedInUser().getValue();

        if (input.contains("@")){
            friendRequestReceiver = userRepository.getUserWithEmail(input);
        } else {
            friendRequestReceiver = userRepository.getUserWithUsername(input);
        }

        String senderUID = FirebaseAuth.getInstance().getUid();

        friendRepository.sendFriendRequest(friendRequestReceiver, senderUID);

    }
}
