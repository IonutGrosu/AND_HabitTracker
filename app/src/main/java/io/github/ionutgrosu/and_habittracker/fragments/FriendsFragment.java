package io.github.ionutgrosu.and_habittracker.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;

import io.github.ionutgrosu.and_habittracker.activities.MainActivity;
import io.github.ionutgrosu.and_habittracker.R;
import io.github.ionutgrosu.and_habittracker.viewModels.UserViewModel;

public class FriendsFragment extends Fragment {

    private TextInputEditText friendRequestInput;
    private Button friendRequestBtn;

    private UserViewModel userViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
    }

    private void handleFriendRequest() {
        String input = friendRequestInput.getText().toString().trim();

        if (input.isEmpty()){
            friendRequestInput.setError("Enter username or email");
            friendRequestInput.requestFocus();
            return;
        }

        userViewModel.sendFriendRequest(input);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_friends, container, false);

        initViews(view);

        friendRequestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleFriendRequest();
            }
        });

        return view;
    }

    private void initViews(View view) {
        ((MainActivity) getActivity()).setTopbarTitle("Friends");

        friendRequestInput = view.findViewById(R.id.friendRequestInput);
        friendRequestBtn = view.findViewById(R.id.friendRequestBtn);
    }
}