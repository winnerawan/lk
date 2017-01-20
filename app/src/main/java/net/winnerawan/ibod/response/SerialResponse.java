/*
 * Copyright (c) 2016. Wonderwall.biz.id | Winnerawan.net .
 * Coder @author Winnerawan T
 *
 */

package net.winnerawan.ibod.response;

/**
 * Created by winnerawan on 12/15/16.
 */

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import net.winnerawan.ibod.model.Serial;

public class SerialResponse {

    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("serial")
    @Expose
    private List<Serial> serial = null;

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public List<Serial> getSerial() {
        return serial;
    }

    public void setSerial(List<Serial> serial) {
        this.serial = serial;
    }

}