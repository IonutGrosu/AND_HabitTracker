package io.github.ionutgrosu.and_habittracker.repositories;


import androidx.lifecycle.LiveData;

import io.github.ionutgrosu.and_habittracker.DAOs.UserDAO;
import io.github.ionutgrosu.and_habittracker.models.User;

public class UserRepository {
    private static UserRepository instance;
    private UserDAO userDAO;

    private UserRepository() {
        userDAO = UserDAO.getInstance();
    }

    public static UserRepository getInstance(){
        if (instance == null){
            instance = new UserRepository();
        }
        return instance;
    }

    public LiveData<User> getLoggedInUser(){
        return userDAO.getLoggedInUser();
    }

}
