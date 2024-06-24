package com.example.moviestmp.controller;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviestmp.R;
import com.example.moviestmp.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

public class KeepWatchingAdapter extends RecyclerView.Adapter<KeepWatchingAdapter.MovieViewHolder>{
    private List<Movie> movies;
    private Boolean keepWatching;

    public KeepWatchingAdapter(List<Movie> movies,Boolean keepWatching) {
        this.movies = movies;
        this.keepWatching=keepWatching;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);
        return new MovieViewHolder(view,keepWatching);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Movie movie = movies.get(position);
        Picasso.get().load("https://image.tmdb.org/t/p/w500/" + movie.getPosterPath()).into(holder.movieImage);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        private ImageView movieImage;
        private ImageView playIcon;

        public MovieViewHolder(@NonNull View itemView,Boolean keepWatching) {
            super(itemView);
            movieImage = itemView.findViewById(R.id.movie_image);
            playIcon = itemView.findViewById(R.id.play_icon);
            if(keepWatching){
                playIcon.setVisibility(View.VISIBLE);
            } else {
                playIcon.setVisibility(View.GONE);
            }

        }
    }
}
