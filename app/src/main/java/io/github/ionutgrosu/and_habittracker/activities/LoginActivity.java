package io.github.ionutgrosu.and_habittracker.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

import io.github.ionutgrosu.and_habittracker.R;

public class LoginActivity extends AppCompatActivity {

    private Button goToRegisterBtn;
    private Button loginBtn;
    private TextInputEditText loginEmailInput;
    private TextInputEditText loginPasswordInput;
    private ProgressBar progressBar;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initViews();

        mAuth = FirebaseAuth.getInstance();

        goToRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    public void loginUser(View v) {
        String email = loginEmailInput.getText().toString().trim();
        String password = loginPasswordInput.getText().toString().trim();

        if (email.isEmpty()) {
            loginEmailInput.setError("Email is required.");
            loginEmailInput.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            loginEmailInput.setError("Provide a valid email address.");
            loginEmailInput.requestFocus();
            return;
        }

        if (password.isEmpty()){
            loginPasswordInput.setError("Password is required.");
            loginPasswordInput.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if (task.isSuccessful()){
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                } else {
                    Toast.makeText(LoginActivity.this, "Failed to log in", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void initViews(){
        loginBtn = findViewById(R.id.loginButton);
        goToRegisterBtn = findViewById(R.id.loginRegisterLink);
        loginEmailInput = findViewById(R.id.loginEmailInput);
        loginPasswordInput = findViewById(R.id.loginPasswordInput);
        progressBar = findViewById(R.id.loginPB);
        progressBar.setVisibility(View.GONE);
    }
}