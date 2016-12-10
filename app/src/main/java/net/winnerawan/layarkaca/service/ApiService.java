/*
 * Copyright (c) 2016. Wonderwall.biz.id | Winnerawan.net .
 * Coder @author Winnerawan T
 *
 */

package net.winnerawan.layarkaca.service;

import net.winnerawan.layarkaca.model.Movie;
import net.winnerawan.layarkaca.response.MovieResponse;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by winnerawan on 12/10/16.
 */

public interface ApiService {

    @GET("/v1/newMovies")
    void getNewMovies(Callback<MovieResponse> mr);

    @GET("/v1/allMovies")
    void getAllMovies(Callback<MovieResponse> mr);

    @GET("/v1/movie/{id}")
    void getMovie(@Path("id") int id, Callback<Movie> m);
}
