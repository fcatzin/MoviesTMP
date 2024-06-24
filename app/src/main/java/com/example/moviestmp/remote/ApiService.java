package com.example.moviestmp.remote;

import com.example.moviestmp.model.MovieResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("movie/now_playing")
    Call<MovieResponse> getNowPlayingMovies();
}
