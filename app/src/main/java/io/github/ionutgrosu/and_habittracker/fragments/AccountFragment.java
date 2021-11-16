package io.github.ionutgrosu.and_habittracker.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import io.github.ionutgrosu.and_habittracker.activities.MainActivity;
import io.github.ionutgrosu.and_habittracker.R;
import io.github.ionutgrosu.and_habittracker.models.User;
import io.github.ionutgrosu.and_habittracker.viewModels.UserViewModel;

public class AccountFragment extends Fragment {

    private TextView accountInfoText;
    private UserViewModel userViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        userViewModel.getLoggedInUser().observe(this, user -> {
            updateTextView(user);
        });
    }

    private void updateTextView(User user) {
        accountInfoText.setText(user.toString());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        initViews(view);
        return view;
    }


    private void initViews(View view) {
        ((MainActivity) getActivity()).setTopbarTitle("Account");

        accountInfoText = view.findViewById(R.id.accountInfoText);
    }
}