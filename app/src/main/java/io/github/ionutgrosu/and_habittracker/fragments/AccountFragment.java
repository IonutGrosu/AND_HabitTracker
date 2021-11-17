package io.github.ionutgrosu.and_habittracker.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.auth.FirebaseAuth;

import io.github.ionutgrosu.and_habittracker.activities.LoginActivity;
import io.github.ionutgrosu.and_habittracker.activities.MainActivity;
import io.github.ionutgrosu.and_habittracker.R;
import io.github.ionutgrosu.and_habittracker.models.User;
import io.github.ionutgrosu.and_habittracker.utils.GlideApp;
import io.github.ionutgrosu.and_habittracker.viewModels.UserViewModel;

public class AccountFragment extends Fragment {

    private TextView helloUsernameText;
    private ImageView imageView;
    private Button logOutButton;
    private ProgressBar progressBar;

    private UserViewModel userViewModel;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        userViewModel.getLoggedInUser().observe(this, user -> {
            updateTextView(user);
            updateImageView(FirebaseAuth.getInstance().getUid());
            progressBar.setVisibility(View.GONE);
            helloUsernameText.setVisibility(View.VISIBLE);
            logOutButton.setVisibility(View.VISIBLE);
            imageView.setVisibility(View.VISIBLE);
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        initViews(view);

        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getActivity(), LoginActivity.class));
            }
        });

        return view;
    }

    private void updateImageView(String uid) {
        GlideApp.with(this)
                .load("https://api.kwelo.com/v1/media/identicon/" + uid)
                .apply(new RequestOptions().override(240, 240))
                .apply(new RequestOptions().transform(new RoundedCorners(8)))
                .placeholder(R.drawable.ic_baseline_no_accounts_24)
                .into(imageView);
    }

    private void updateTextView(User user) {
        helloUsernameText.setText("Hello,\n" + user.getUsername());
    }

    private void initViews(View view) {
        ((MainActivity) getActivity()).setTopbarTitle("Account");

        helloUsernameText = view.findViewById(R.id.helloUsernameText);
        helloUsernameText.setVisibility(View.GONE);
        logOutButton = view.findViewById(R.id.logOutButton);
        logOutButton.setVisibility(View.GONE);
        imageView = view.findViewById(R.id.imageView);
        imageView.setVisibility(View.GONE);
        progressBar = view.findViewById(R.id.accountInfoPB);
    }
}