/*
 * Copyright (c) 2016. Wonderwall.biz.id | Winnerawan.net .
 * Coder @author Winnerawan T
 *
 */

package net.winnerawan.wonderscreen.response;

/**
 * Created by winnerawan on 12/15/16.
 */

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import net.winnerawan.wonderscreen.model.Episode;

public class EpisodeResponse {

    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("season")
    @Expose
    private Integer season;
    @SerializedName("trailer")
    @Expose
    private String trailer;
    @SerializedName("genre")
    @Expose
    private String genre;
    @SerializedName("director")
    @Expose
    private String director;
    @SerializedName("quality")
    @Expose
    private String quality;
    @SerializedName("release")
    @Expose
    private String release;
    @SerializedName("actor")
    @Expose
    private String actor;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("imdb_rating")
    @Expose
    private Double imdbRating;
    @SerializedName("synopsis")
    @Expose
    private String synopsis;
    @SerializedName("post_date")
    @Expose
    private String postDate;
    @SerializedName("hits")
    @Expose
    private Integer hits;
    @SerializedName("list_episode")
    @Expose
    private List<Episode> listEpisode = null;

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getSeason() {
        return season;
    }

    public void setSeason(Integer season) {
        this.season = season;
    }

    public String getTrailer() {
        return trailer;
    }

    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public String getRelease() {
        return release;
    }

    public void setRelease(String release) {
        this.release = release;
    }

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Double getImdbRating() {
        return imdbRating;
    }

    public void setImdbRating(Double imdbRating) {
        this.imdbRating = imdbRating;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public String getPostDate() {
        return postDate;
    }

    public void setPostDate(String postDate) {
        this.postDate = postDate;
    }

    public Integer getHits() {
        return hits;
    }

    public void setHits(Integer hits) {
        this.hits = hits;
    }

    public List<Episode> getListEpisode() {
        return listEpisode;
    }

    public void setListEpisode(List<Episode> listEpisode) {
        this.listEpisode = listEpisode;
    }
}
