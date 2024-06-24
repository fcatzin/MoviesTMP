package com.example.moviestmp.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.moviestmp.R;
import com.example.moviestmp.controller.KeepWatchingAdapter;
import com.example.moviestmp.controller.SliderAdapter;
import com.example.moviestmp.controller.UserController;
import com.example.moviestmp.listener.OnMovieClickListener;
import com.example.moviestmp.model.Movie;
import com.example.moviestmp.model.MovieDetail;
import com.example.moviestmp.model.MovieResponse;
import com.example.moviestmp.remote.ApiClient;
import com.example.moviestmp.remote.ApiService;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity implements UserController.ViewLogout,OnMovieClickListener {
    private ViewPager2 viewPager2;
    private SliderAdapter sliderAdapter;

    private KeepWatchingAdapter seguirViendoAdapter;
    private RecyclerView seguirViendoRecyclerView;
    private RecyclerView masPopularesRecyclerView;
    private KeepWatchingAdapter masPopularesAdapter;
    private ImageView avatarImageView;
    private UserController userController;
    ApiService apiService;
    private TextView profileName;
    private final String BEARER_TOKEN="eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJjMDgyMzkzNDQzODA3NWQ2M2YxZGJkYTQwMjNlNzZmYyIsInN1YiI6IjY1MDBmNzJkNTU0NWNhMDExYmE2N2RkYyIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.4QxbpZq9Tj3uzhA8uv2qLNcCA7NIcGBHDzoC4bWv9t8";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        userController = new UserController(this);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            String text =getString(R.string.menu_logout);
            if(text.equals(item.getTitle().toString())){
                userController.logout();
                return true;
            }
            return false;
        });

        int SELECTED_PROFILE=(int)getIntent().getSerializableExtra("SELECTED_PROFILE");
        String SELECTED_PROFILE_NAME=(String)getIntent().getSerializableExtra("SELECTED_PROFILE_NAME");
        avatarImageView=findViewById(R.id.profile_icon);
        avatarImageView.setImageResource(SELECTED_PROFILE);
        profileName=findViewById(R.id.profile_name);
        profileName.setText(SELECTED_PROFILE_NAME);
        viewPager2 = findViewById(R.id.slider);
        seguirViendoRecyclerView = findViewById(R.id.seguir_viendo_recycler);
        masPopularesRecyclerView = findViewById(R.id.mas_populares_recycler);

        apiService = ApiClient.getClient(BEARER_TOKEN).create(ApiService.class);
        Call<MovieResponse> call = apiService.getNowPlayingMovies();

        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.isSuccessful()) {
                    List<Movie> movies = response.body().getMovies();
                    int partitionSize = movies.size()/ 3;
                    int remainder = movies.size() % 3;

                    List<Movie> listSlider = movies.subList(0, partitionSize + (remainder > 0 ? 1 : 0));
                    List<Movie> listSeguirViendo = movies.subList(partitionSize + (remainder > 0 ? 1 : 0), 2 * partitionSize + (remainder > 1 ? 1 : 0));
                    List<Movie> listPupulares = movies.subList(2 * partitionSize + (remainder > 1 ? 1 : 0), movies.size());

                    sliderAdapter = new SliderAdapter(listSlider,HomeActivity.this);
                    viewPager2.setAdapter(sliderAdapter);

                    // Configurar RecyclerView para "Seguir viendo"
                    seguirViendoRecyclerView.setLayoutManager(new LinearLayoutManager(HomeActivity.this, LinearLayoutManager.HORIZONTAL, false));
                    seguirViendoAdapter = new KeepWatchingAdapter(listSeguirViendo,true,HomeActivity.this);
                    seguirViendoRecyclerView.setAdapter(seguirViendoAdapter);

                    // Configurar RecyclerView para "Más populares"
                    masPopularesRecyclerView.setLayoutManager(new LinearLayoutManager(HomeActivity.this, LinearLayoutManager.HORIZONTAL, false));
                    masPopularesAdapter = new KeepWatchingAdapter(listPupulares,false,HomeActivity.this);
                    masPopularesRecyclerView.setAdapter(masPopularesAdapter);
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
            }
        });
    }



    @Override
    public void onLogoutSuccess() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onLogoutFailure(String message) {

    }

    @Override
    public void onMovieClick(int movieId) {
        Intent intent = new Intent(this, MovieDetailActivity.class);
        intent.putExtra("MOVIE_ID", movieId);
        startActivity(intent);
        //finish();
    }
}
