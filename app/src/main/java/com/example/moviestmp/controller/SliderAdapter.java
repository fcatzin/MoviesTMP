package com.example.moviestmp.controller;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviestmp.R;
import com.example.moviestmp.listener.OnMovieClickListener;
import com.example.moviestmp.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SliderAdapter extends RecyclerView.Adapter<SliderAdapter.SliderViewHolder>{
    private List<Movie> movies;
    private OnMovieClickListener listener;

    public SliderAdapter(List<Movie> movies, OnMovieClickListener listener) {
        this.movies = movies;
        this.listener=listener;
    }

    @NonNull
    @Override
    public SliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.slider_item, parent, false);
        return new SliderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SliderViewHolder holder, int position) {
        Movie movie = movies.get(position);
        holder.titleTextView.setText(movie.getTitle());
        Picasso.get().load("https://image.tmdb.org/t/p/w500/" + movie.getPosterPath()).into(holder.imageView);
        holder.itemView.setOnClickListener(item->listener.onMovieClick(movie.getId()));
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public static class SliderViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;
        private TextView titleTextView;

        public SliderViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
        }
    }
}
