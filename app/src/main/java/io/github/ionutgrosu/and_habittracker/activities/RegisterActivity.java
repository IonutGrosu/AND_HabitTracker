package io.github.ionutgrosu.and_habittracker.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import io.github.ionutgrosu.and_habittracker.R;
import io.github.ionutgrosu.and_habittracker.models.User;
import io.github.ionutgrosu.and_habittracker.viewModels.UserViewModel;

public class RegisterActivity extends AppCompatActivity {

    private Button registerBtn;
    private TextInputEditText emailInput;
    private TextInputEditText passwordInput;
    private TextInputEditText usernameInput;
    private ProgressBar progressBar;

    private FirebaseAuth mAuth;

    private UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        initViews();

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createAccount();
            }
        });
    }

    private void createAccount() {
        String username = usernameInput.getText().toString().trim();
        String email = emailInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();

        if (username.isEmpty()){
            usernameInput.setError("Name is required.");
            usernameInput.requestFocus();
            return;
        }

        if (email.isEmpty()) {
            emailInput.setError("Email is required.");
            emailInput.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailInput.setError("Provide a valid email address.");
            emailInput.requestFocus();
            return;
        }

        if (password.isEmpty()){
            passwordInput.setError("Password is required.");
            passwordInput.requestFocus();
            return;
        }

        if (password.length() < 6){
            passwordInput.setError("Password should be at least 6 characters long");
            passwordInput.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);

                try {
                    userViewModel.saveRegisteredUser(username, email, mAuth.getUid());

                    Toast.makeText(getApplicationContext(), "User has been registered successfully", Toast.LENGTH_SHORT).show();
                    
                    startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Authentication failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initViews() {
        registerBtn = findViewById(R.id.signUpButton);
        emailInput = findViewById(R.id.registerEmailInput);
        passwordInput = findViewById(R.id.registerPasswordInput);
        usernameInput = findViewById(R.id.registerUsernameInput);
        progressBar = findViewById(R.id.registerPB);
        progressBar.setVisibility(View.GONE);
    }
}