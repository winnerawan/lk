/*
 * Copyright (c) 2016. Wonderwall.biz.id | Winnerawan.net .
 * Coder @author Winnerawan T
 *
 */

package net.winnerawan.ibod.service;

import net.winnerawan.ibod.response.ServerOneResponse;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by winnerawan on 12/28/16.
 */

public interface ApiServerOne {

    @GET("/data.php?most")
    void getMostMovies(@Query("p") int p, Callback<ServerOneResponse> sr);

}
