package com.example.moviestmp.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviestmp.MainActivity;
import com.example.moviestmp.R;
import com.example.moviestmp.controller.ProfileAdapter;
import com.example.moviestmp.model.Profile;

import java.util.ArrayList;
import java.util.List;

public class ProfileSelectionActivity extends AppCompatActivity implements ProfileAdapter.OnProfileClickListener{
    private RecyclerView recyclerView;
    private ProfileAdapter adapter;
    private List<Profile> profiles;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_selection);

        recyclerView = findViewById(R.id.profiles_recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        profiles = getProfiles();
        adapter = new ProfileAdapter(profiles, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onProfileClick(Profile profile) {
        Toast Toast = null;
        Toast.makeText(this, "Selected profile: " + profile.getName(), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, HomeActivity.class);
        intent.putExtra("SELECTED_PROFILE",  profile.getAvatarResId());
        intent.putExtra("SELECTED_PROFILE_NAME",  profile.getName());
        startActivity(intent);
        finish();
    }

    private List<Profile> getProfiles() {
        List<Profile> profiles = new ArrayList<>();
        profiles.add(new Profile("1", "Alonso", R.drawable.avatar1));
        profiles.add(new Profile("2", "Felipe", R.drawable.avatar2));
        profiles.add(new Profile("3", "Pedro", R.drawable.avatar3));
        profiles.add(new Profile("4", "Escudros", R.drawable.avatar4));
        return profiles;
    }
}
