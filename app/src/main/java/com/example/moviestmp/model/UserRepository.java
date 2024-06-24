package com.example.moviestmp.model;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UserRepository {
    private FirebaseAuth firebaseAuth;

    public UserRepository() {
        this.firebaseAuth = FirebaseAuth.getInstance();
    }

    public void loginUser(String email, String password, final LoginCallback callback) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        callback.onSuccess(user);
                    } else {
                        callback.onFailure(task.getException());
                    }
                });
    }
    public void logout(final LogoutCallback callback){
        firebaseAuth.signOut();
        callback.onSuccess();
    }
    public interface LoginCallback {
        void onSuccess(FirebaseUser user);
        void onFailure(Exception e);
    }
    public interface LogoutCallback {
        void onSuccess();
        void onFailure(Exception e);
    }
}
