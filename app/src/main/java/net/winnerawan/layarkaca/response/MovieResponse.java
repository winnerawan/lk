/*
 * Copyright (c) 2016. Wonderwall.biz.id | Winnerawan.net .
 * Coder @author Winnerawan T
 *
 */

package net.winnerawan.layarkaca.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import net.winnerawan.layarkaca.model.Movie;

import java.util.List;

/**
 * Created by winnerawan on 12/10/16.
 */

public class MovieResponse {
    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("movies")
    @Expose
    private List<Movie> movies = null;

    /**
     *
     * @return
     * The error
     */
    public Boolean getError() {
        return error;
    }

    /**
     *
     * @param error
     * The error
     */
    public void setError(Boolean error) {
        this.error = error;
    }

    /**
     *
     * @return
     * The movies
     */
    public List<Movie> getMovies() {
        return movies;
    }

    /**
     *
     * @param movies
     * The movies
     */
    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }

}
