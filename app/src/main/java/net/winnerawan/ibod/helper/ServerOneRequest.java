/*
 * Copyright (c) 2016. Wonderwall.biz.id | Winnerawan.net .
 * Coder @author Winnerawan T
 *
 */

package net.winnerawan.ibod.helper;

import net.winnerawan.ibod.app.AppConfig;

import retrofit.RestAdapter;

/**
 * Created by winnerawan on 12/28/16.
 */

public class ServerOneRequest {

    public RestAdapter RequestMovieFromServerOne() {
        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(AppConfig.SERVER_ONE).build();
        return restAdapter;
    }
}
