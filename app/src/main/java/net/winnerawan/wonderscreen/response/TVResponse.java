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

import net.winnerawan.wonderscreen.model.TV;

public class TVResponse {

    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("tv")
    @Expose
    private List<TV> tv = null;

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
     * The tv
     */
    public List<TV> getTv() {
        return tv;
    }

    /**
     *
     * @param tv
     * The tv
     */
    public void setTv(List<TV> tv) {
        this.tv = tv;
    }

}