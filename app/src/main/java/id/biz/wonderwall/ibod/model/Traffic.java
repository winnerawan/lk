package id.biz.wonderwall.ibod.model;

/**
 * Created by winnerawan on 1/19/17.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Traffic {

    @SerializedName("left")
    @Expose
    private Integer left;
    @SerializedName("used_24h")
    @Expose
    private Integer used24h;

    public Integer getLeft() {
        return left;
    }

    public void setLeft(Integer left) {
        this.left = left;
    }

    public Integer getUsed24h() {
        return used24h;
    }

    public void setUsed24h(Integer used24h) {
        this.used24h = used24h;
    }
}
