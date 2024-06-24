package com.example.moviestmp.view;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.moviestmp.R;
import com.example.moviestmp.model.MovieDetail;
import com.example.moviestmp.remote.ApiClient;
import com.example.moviestmp.remote.ApiService;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetailActivity extends AppCompatActivity {
    ApiService apiService;
    private final String BEARER_TOKEN="eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJjMDgyMzkzNDQzODA3NWQ2M2YxZGJkYTQwMjNlNzZmYyIsInN1YiI6IjY1MDBmNzJkNTU0NWNhMDExYmE2N2RkYyIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.4QxbpZq9Tj3uzhA8uv2qLNcCA7NIcGBHDzoC4bWv9t8";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        apiService = ApiClient.getClient(BEARER_TOKEN).create(ApiService.class);
        int movieId = getIntent().getIntExtra("MOVIE_ID", -1);

        if (movieId != -1) {
            fetchMovieDetail(movieId);
        } else {
            Toast.makeText(this, "Error: Movie ID not found", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void showMovieDetailDialog(MovieDetail movieDetail) {
        LayoutInflater inflater = LayoutInflater.from(this);
        View dialogView = inflater.inflate(R.layout.activity_movie_detail, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);

        ImageView imageViewMovie = dialogView.findViewById(R.id.imageViewMovie);
        TextView textViewMovieName = dialogView.findViewById(R.id.textViewMovieName);
        TextView textViewMovieIdValue = dialogView.findViewById(R.id.textViewMovieIdValue);
        TextView textViewMovieOverview = dialogView.findViewById(R.id.textViewMovieOverview);
        TextView textViewMovieReleaseDate = dialogView.findViewById(R.id.textViewMovieReleaseDate);

        textViewMovieName.setText(movieDetail.getTitle());
        textViewMovieIdValue.setText(String.valueOf(movieDetail.getId()));
        textViewMovieOverview.setText(String.valueOf(movieDetail.getOverview()));
        textViewMovieReleaseDate.setText(String.valueOf(movieDetail.getReleaseDate()));

        Picasso.get().load("https://image.tmdb.org/t/p/w500/" + movieDetail.getPosterPath()).into(imageViewMovie);

        AlertDialog dialog = builder.create();

        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    dialog.dismiss();
                    finish();
                    return true;
                }
                return false;
            }
        });

        dialog.show();
    }

    private void fetchMovieDetail(int movieId) {
        apiService.getMovieDetails(movieId).enqueue(new Callback<MovieDetail>() {
            @Override
            public void onResponse(Call<MovieDetail> call, Response<MovieDetail> response) {
                if (response.isSuccessful()) {
                    MovieDetail movieDetail = response.body();
                    showMovieDetailDialog(movieDetail);
                } else {
                    Toast.makeText(MovieDetailActivity.this, "Error: Could not get details.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MovieDetail> call, Throwable t) {
                Toast.makeText(MovieDetailActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
