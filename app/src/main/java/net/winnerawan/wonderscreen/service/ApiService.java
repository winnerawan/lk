/*
 * Copyright (c) 2016. Wonderwall.biz.id | Winnerawan.net .
 * Coder @author Winnerawan T
 *
 */

package net.winnerawan.wonderscreen.service;

import net.winnerawan.wonderscreen.model.Anime;
import net.winnerawan.wonderscreen.model.Movie;
import net.winnerawan.wonderscreen.model.Serial;
import net.winnerawan.wonderscreen.model.WatchSerial;
import net.winnerawan.wonderscreen.response.EpisodeResponse;
import net.winnerawan.wonderscreen.response.MovieResponse;
import net.winnerawan.wonderscreen.response.SerialResponse;
import net.winnerawan.wonderscreen.response.TVResponse;

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

    @GET("/v1/popMovies")
    void getPopularMovies(Callback<MovieResponse> mr);

    @GET("/v1/semiMovies")
    void getSemiMovies(Callback<MovieResponse> mr);

    @GET("/v1/semimovie/{id}")
    void getSemiMovie(@Path("id") int id, Callback<Movie> m);

    @GET("/v1/serialMovies")
    void getSerialMovies(Callback<SerialResponse> sr);

    @GET("/v1/serial/{id}")
    void getSerial(@Path("id") int id, Callback<Serial> sr);

    @GET("/v1/fullSerial/{id}")
    void getFullSerial(@Path("id") int id, Callback<EpisodeResponse> er);

    @GET("/v1/serial/{serial_id}/episode/{episode}")
    void getSerialEpisode(@Path("serial_id") int serial_id, @Path("episode") int episode, Callback<WatchSerial> wr);

    @GET("/v1/listAnime")
    void getListAnime(Callback<SerialResponse> sr);

    @GET("/v1/anime/{id}")
    void getAnime(@Path("id") int id, Callback<Anime> ar);

    @GET("/v1/fullAnime/{id}")
    void getFullAnime(@Path("id") int id, Callback<EpisodeResponse> er);

    @GET("/v1/anime/{anime_id}/episode/{episode}")
    void getAnimeEpisode(@Path("anime_id") int anime_id, @Path("episode") int episode, Callback<WatchSerial> wr);

    @GET("/v1/liveStream")
    void getListTV(Callback<TVResponse> tr);

}
