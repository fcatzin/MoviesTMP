package com.example.moviestmp.controller;

import com.example.moviestmp.model.UserRepository;
import com.google.firebase.auth.FirebaseUser;

public class UserController {
    private UserRepository userRepository;
    private View view;

    public UserController(View view) {
        this.view = view;
        this.userRepository = new UserRepository();
    }

    public void login(String email, String password) {
        userRepository.loginUser(email, password, new UserRepository.LoginCallback() {
            @Override
            public void onSuccess(FirebaseUser user) {
                view.onLoginSuccess(user);
            }

            @Override
            public void onFailure(Exception e) {
                view.onLoginFailure(e.getMessage());
            }
        });
    }

    public interface View {
        void onLoginSuccess(FirebaseUser user);
        void onLoginFailure(String message);
    }
}
