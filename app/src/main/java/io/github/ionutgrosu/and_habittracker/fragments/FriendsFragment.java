package io.github.ionutgrosu.and_habittracker.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

import io.github.ionutgrosu.and_habittracker.activities.MainActivity;
import io.github.ionutgrosu.and_habittracker.R;
import io.github.ionutgrosu.and_habittracker.models.User;
import io.github.ionutgrosu.and_habittracker.viewModels.FriendListAdapter;
import io.github.ionutgrosu.and_habittracker.viewModels.FriendRequestAdapter;
import io.github.ionutgrosu.and_habittracker.viewModels.UserViewModel;

public class FriendsFragment extends Fragment {

    private TextInputEditText friendRequestInput;
    private Button friendRequestBtn;

    private UserViewModel userViewModel;

    private RecyclerView friendRequestsRV;
    private FriendRequestAdapter friendRequestAdapter;
    private TextView noFriendsRequestsTextView;

    private RecyclerView friendListRV;
    private FriendListAdapter friendListAdapter;
    private TextView noFriendsTextView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        friendRequestAdapter = new FriendRequestAdapter(new ArrayList<User>(), getActivity());
        userViewModel.getFriendRequests();

        userViewModel.usersRequestingFriendship.observe(this, new Observer<ArrayList<User>>() {
            @Override
            public void onChanged(ArrayList<User> users) {
                System.out.println(users.toString());
                friendRequestsRV.setAdapter(friendRequestAdapter);
                friendRequestAdapter.setRequestingUsers(users);
            }
        });

        friendListAdapter = new FriendListAdapter(new ArrayList<User>(), getActivity());
        userViewModel.getFriends();
    }

    @Override
    public void onResume() {
        super.onResume();

        userViewModel.getFriendRequests();

        userViewModel.usersRequestingFriendship.observe(getViewLifecycleOwner(), new Observer<ArrayList<User>>() {
            @Override
            public void onChanged(ArrayList<User> users) {
                if (users.isEmpty()){
                    noFriendsRequestsTextView.setVisibility(View.VISIBLE);
                } else{
                    noFriendsRequestsTextView.setVisibility(View.GONE);
                }
                friendRequestsRV.setAdapter(friendRequestAdapter);
                friendRequestAdapter.setRequestingUsers(users);
            }
        });

        userViewModel.getFriends();

        userViewModel.friends.observe(this, new Observer<ArrayList<User>>() {
            @Override
            public void onChanged(ArrayList<User> users) {
                if (users.isEmpty()){
                    noFriendsTextView.setVisibility(View.VISIBLE);
                } else{
                    noFriendsTextView.setVisibility(View.GONE);
                }
                friendListRV.setAdapter(friendListAdapter);
                friendListAdapter.setFriends(users);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_friends, container, false);

        initViews(view);

        friendRequestsRV.setAdapter(friendRequestAdapter);

        friendRequestAdapter.setFriendRequestManager(new FriendRequestAdapter.FriendRequestManager() {
            @Override
            public void acceptRequest(User user) {
                userViewModel.acceptFriendRequest(user);
            }

            @Override
            public void declineRequest(User user) {
                userViewModel.declineFriendRequest(user);
                userViewModel.getFriendRequests();
            }
        });

        friendListAdapter.setFriendListManager(new FriendListAdapter.FriendListManager() {
            @Override
            public void removeFriend(User user) {
                userViewModel.removeFriend(user);
            }
        });

        friendRequestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleFriendRequest();
            }
        });

        return view;
    }


    private void handleFriendRequest() {
        String input = friendRequestInput.getText().toString().trim();
        if (input.isEmpty()){
            friendRequestInput.setError("Enter username or email");
            friendRequestInput.requestFocus();
            return;
        }

        userViewModel.sendFriendRequest(input);
        friendRequestInput.setText("");
    }

    private void initViews(View view) {
        ((MainActivity) getActivity()).setTopbarTitle("Friends");

        friendRequestsRV = view.findViewById(R.id.friendRequestsRV);
        friendRequestsRV.hasFixedSize();
        friendRequestsRV.setLayoutManager(new LinearLayoutManager(getContext()));
        noFriendsRequestsTextView = view.findViewById(R.id.noFriendsRequestsTextView);

        friendListRV = view.findViewById(R.id.friendsListRV);
        friendListRV.hasFixedSize();
        friendListRV.setLayoutManager(new LinearLayoutManager(getContext()));
        noFriendsTextView = view.findViewById(R.id.noFriendsTextView);

        friendRequestInput = view.findViewById(R.id.friendRequestInput);
        friendRequestBtn = view.findViewById(R.id.friendRequestBtn);
    }
}