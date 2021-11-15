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

public class RegisterActivity extends AppCompatActivity {

    private Button registerBtn;
    private TextInputEditText emailInput;
    private TextInputEditText passwordInput;
    private ProgressBar progressBar;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        initViews();

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createAccount();
            }
        });
    }

    private void createAccount() {
        String email = emailInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();

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

        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if (task.isSuccessful()){
                    Log.d("mauth", "createUserWithEmail:success");

                    startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                } else {
                    Log.w("mauth", "createUserWithEmail:failure", task.getException());
                    Toast.makeText(getApplicationContext(), "Authentication failed.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initViews() {
        registerBtn = findViewById(R.id.signUpButton);
        emailInput = findViewById(R.id.registerEmailInput);
        passwordInput = findViewById(R.id.registerPasswordInput);
        progressBar = findViewById(R.id.registerPB);
        progressBar.setVisibility(View.GONE);
    }
}