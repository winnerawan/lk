/*
 * Copyright (c) 2016. Wonderwall.biz.id | Winnerawan.net .
 * Coder @author Winnerawan T
 *
 */

package net.winnerawan.layarkaca.service;

import net.winnerawan.layarkaca.response.MovieResponse;

import retrofit.Callback;
import retrofit.http.GET;

/**
 * Created by winnerawan on 12/10/16.
 */

public interface ApiService {

    @GET("/v1/newMovies")
    void getNewMovies(Callback<MovieResponse> mr);
}
