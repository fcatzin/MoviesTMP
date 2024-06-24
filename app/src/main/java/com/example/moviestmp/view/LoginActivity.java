package com.example.moviestmp.view;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.moviestmp.R;
import com.example.moviestmp.controller.UserController;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity implements UserController.View {
    private UserController userController;
    private EditText emailEditText, passwordEditText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        findViewById(R.id.loginButton).setOnClickListener(this::onLoginClicked);

        userController = new UserController(this);
    }

    private void onLoginClicked(View view) {
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Email and Password cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        userController.login(email, password);
    }

    @Override
    public void onLoginSuccess(FirebaseUser user) {
        Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLoginFailure(String message) {
        Toast.makeText(this, "Login Failed: " + message, Toast.LENGTH_SHORT).show();
    }
}
