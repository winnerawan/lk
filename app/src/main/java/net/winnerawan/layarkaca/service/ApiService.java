/*
 * Copyright (c) 2016. Wonderwall.biz.id | Winnerawan.net .
 * Coder @author Winnerawan T
 *
 */

package net.winnerawan.layarkaca.service;

import net.winnerawan.layarkaca.model.Movie;
import net.winnerawan.layarkaca.model.MovieArray;
import net.winnerawan.layarkaca.model.Serial;
import net.winnerawan.layarkaca.response.EpisodeResponse;
import net.winnerawan.layarkaca.response.MovieArrayResponse;
import net.winnerawan.layarkaca.response.MovieResponse;
import net.winnerawan.layarkaca.response.SerialResponse;
import net.winnerawan.layarkaca.response.TVResponse;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by winnerawan on 12/10/16.
 */

public interface ApiService {

    @GET("/v1/newMovies")
    void getNewMovies(Callback<MovieResponse> mr);

    @GET("/v1/newMovies")
    void getNewMoviesArray(Callback<MovieArrayResponse> mr);

    @GET("/v1/allMovies")
    void getAllMovies(Callback<MovieResponse> mr);

    @GET("/v1/movie/{id}")
    void getMovie(@Path("id") int id, Callback<Movie> m);

    @GET("/v1/popMovies")
    void getPopularMovies(Callback<MovieResponse> mr);

    @GET("/v1/semiMovies")
    void getSemiMovies(Callback<MovieResponse> mr);

    @GET("/v1/serialMovies")
    void getSerialMovies(Callback<SerialResponse> sr);

    @GET("/v1/serial/{id}")
    void getSerial(@Path("id") int id, Callback<Serial> sr);

    @GET("/v1/fullSerial/{id}")
    void getFullSerial(@Path("id") int id, Callback<EpisodeResponse> er);

    @GET("/v1/listAnime")
    void getListAnime(Callback<SerialResponse> sr);

    @GET("/v1/liveStream")
    void getListTV(Callback<TVResponse> tr);

}
