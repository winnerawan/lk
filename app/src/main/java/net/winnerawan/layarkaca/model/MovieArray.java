/*
 * Copyright (c) 2016. Wonderwall.biz.id | Winnerawan.net .
 * Coder @author Winnerawan T
 *
 */

package net.winnerawan.layarkaca.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by winnerawan on 12/16/16.
 */

public class MovieArray {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("poster")
    @Expose
    private String poster;
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
    private String imdbRating;
    @SerializedName("duration")
    @Expose
    private String duration;
    @SerializedName("synopsis")
    @Expose
    private String synopsis;
    @SerializedName("review")
    @Expose
    private String review;
    @SerializedName("post_date")
    @Expose
    private String postDate;
    @SerializedName("link360")
    @Expose
    private String link360;
    @SerializedName("link480")
    @Expose
    private String link480;
    @SerializedName("link720")
    @Expose
    private String link720;
    @SerializedName("hits")
    @Expose
    private String hits;

    public MovieArray() {
    }

    public MovieArray(String id, String title, String image, String poster, String trailer, String genre, String director, String quality,
                 String release, String actor, String country, String imdbRating, String duration, String synopsis, String review,
                 String postDate, String link360, String link480, String link720, String hits) {
        this.id=id;
        this.title=title;
        this.image=image;
        this.poster=poster;
        this.trailer=trailer;
        this.genre=genre;
        this.director=director;
        this.quality=quality;
        this.release=release;
        this.actor=actor;
        this.country=country;
        this.imdbRating=imdbRating;
        this.duration=duration;
        this.synopsis=synopsis;
        this.review=review;
        this.postDate=postDate;
        this.link360=link360;
        this.link480=link480;
        this.link720=link720;
        this.hits=hits;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
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

    public String getImdbRating() {
        return imdbRating;
    }

    public void setImdbRating(String imdbRating) {
        this.imdbRating = imdbRating;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
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

    public String getLink360() {
        return link360;
    }

    public void setLink360(String link360) {
        this.link360 = link360;
    }

    public String getLink480() {
        return link480;
    }

    public void setLink480(String link480) {
        this.link480 = link480;
    }

    public String getLink720() {
        return link720;
    }

    public void setLink720(String link720) {
        this.link720 = link720;
    }

    public String getHits() {
        return hits;
    }

    public void setHits(String hits) {
        this.hits = hits;
    }
}