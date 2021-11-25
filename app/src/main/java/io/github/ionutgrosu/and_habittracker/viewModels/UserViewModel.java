package io.github.ionutgrosu.and_habittracker.viewModels;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import io.github.ionutgrosu.and_habittracker.models.User;
import io.github.ionutgrosu.and_habittracker.repositories.UserRepository;

public class UserViewModel extends ViewModel {

    private UserRepository userRepository;

    public UserViewModel() {
        userRepository = UserRepository.getInstance();
    }

    public LiveData<User> getLoggedInUser(){
        return userRepository.getLoggedInUser();
    }

    public void saveRegisteredUser(String username, String email, String uid) throws Exception {
        User userToSave = new User(username, email, uid);

        userRepository.saveUser(userToSave);

    }
}
