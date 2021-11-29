package io.github.ionutgrosu.and_habittracker.models;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String username;
    private String email;
    private String uid;
    private List<String> friendsUids = new ArrayList<>();

    public User() {
    }

    public User(String username, String email, String uid) {
        this.username = username;
        this.email = email;
        this.uid = uid;
    }

    public void addFriend(String uid){
        friendsUids.add(uid);
    }

    public void removeFriend(String uid) {friendsUids.remove(uid);}

    public List<String> getFriendsUids(){
        return friendsUids;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
