package com.example.moviestmp.controller;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviestmp.R;
import com.example.moviestmp.model.Profile;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.ProfileViewHolder>{
    private List<Profile> profiles;
    private OnProfileClickListener listener;

    public ProfileAdapter(List<Profile> profiles, OnProfileClickListener listener) {
        this.profiles = profiles;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ProfileViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_profile, parent, false);
        return new ProfileViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProfileViewHolder holder, int position) {
        Profile profile = profiles.get(position);
        holder.bind(profile, listener);
    }

    @Override
    public int getItemCount() {
        return profiles.size();
    }

    public interface OnProfileClickListener {
        void onProfileClick(Profile profile);
    }

    public static class ProfileViewHolder extends RecyclerView.ViewHolder {

        private ImageView avatarImageView;
        private TextView nameTextView;

        public ProfileViewHolder(@NonNull View itemView) {
            super(itemView);
            avatarImageView = itemView.findViewById(R.id.avatar_image_view);
            nameTextView = itemView.findViewById(R.id.name_text_view);
        }

        public void bind(Profile profile, OnProfileClickListener listener) {
            nameTextView.setText(profile.getName());

            if (profile.getAvatarUrl() != null) {
                Picasso.get()
                        .load(profile.getAvatarUrl())
                        .into(avatarImageView);
            } else if (profile.getAvatarResId() != 0) {
                avatarImageView.setImageResource(profile.getAvatarResId());
            }

            itemView.setOnClickListener(v -> listener.onProfileClick(profile));
        }
    }
}
