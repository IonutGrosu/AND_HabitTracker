package io.github.ionutgrosu.and_habittracker.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;

import io.github.ionutgrosu.and_habittracker.activities.MainActivity;
import io.github.ionutgrosu.and_habittracker.R;

public class FriendsFragment extends Fragment {

    private TextInputEditText friendRequestInput;
    private Button friendRequestBtn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_friends, container, false);

        initViews(view);

        return view;
    }

    private void initViews(View view) {
        ((MainActivity) getActivity()).setTopbarTitle("Friends");

        friendRequestInput = view.findViewById(R.id.friendRequestInput);
        friendRequestBtn = view.findViewById(R.id.friendRequestBtn);
    }
}