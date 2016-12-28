/*
 * Copyright (c) 2016. Wonderwall.biz.id | Winnerawan.net .
 * Coder @author Winnerawan T
 *
 */

package net.winnerawan.wonderscreen.model;

/**
 * Created by winnerawan on 12/28/16.
 */

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Video {

    @SerializedName("url_social")
    @Expose
    private String urlSocial;
    @SerializedName("video")
    @Expose
    private Video_ video;
    @SerializedName("stats")
    @Expose
    private Stats stats;
    @SerializedName("category_id")
    @Expose
    private Integer categoryId;
    @SerializedName("another_category_ids")
    @Expose
    private List<Object> anotherCategoryIds = null;
    @SerializedName("series")
    @Expose
    private String series;
    @SerializedName("author")
    @Expose
    private String author;
    @SerializedName("url_image")
    @Expose
    private String urlImage;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("update_at")
    @Expose
    private String updateAt;
    @SerializedName("create_at")
    @Expose
    private String createAt;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("vip_play")
    @Expose
    private Integer vipPlay;

    public String getUrlSocial() {
        return urlSocial;
    }

    public void setUrlSocial(String urlSocial) {
        this.urlSocial = urlSocial;
    }

    public Video_ getVideo() {
        return video;
    }

    public void setVideo(Video_ video) {
        this.video = video;
    }

    public Stats getStats() {
        return stats;
    }

    public void setStats(Stats stats) {
        this.stats = stats;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public List<Object> getAnotherCategoryIds() {
        return anotherCategoryIds;
    }

    public void setAnotherCategoryIds(List<Object> anotherCategoryIds) {
        this.anotherCategoryIds = anotherCategoryIds;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(String updateAt) {
        this.updateAt = updateAt;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getVipPlay() {
        return vipPlay;
    }

    public void setVipPlay(Integer vipPlay) {
        this.vipPlay = vipPlay;
    }

}