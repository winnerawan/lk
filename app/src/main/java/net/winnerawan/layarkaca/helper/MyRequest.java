/*
 * Copyright (c) 2016. Wonderwall.biz.id | Winnerawan.net .
 * Coder @author Winnerawan T
 *
 */

package net.winnerawan.layarkaca.helper;

import net.winnerawan.layarkaca.app.AppConfig;

import retrofit.RestAdapter;

/**
 * Created by winnerawan on 12/10/16.
 */

public class MyRequest {

    public RestAdapter RequestMovie() {
        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(AppConfig.BASE_URL).build();
        return restAdapter;
    }
}
