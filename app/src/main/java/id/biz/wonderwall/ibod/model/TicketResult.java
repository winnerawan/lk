package id.biz.wonderwall.ibod.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by winnerawan on 1/19/17.
 */

public class TicketResult {

    @SerializedName("ticket")
    @Expose
    private String ticket;
    @SerializedName("captcha_url")
    @Expose
    private Boolean captchaUrl;
    @SerializedName("captcha_w")
    @Expose
    private Boolean captchaW;
    @SerializedName("captcha_h")
    @Expose
    private Boolean captchaH;
    @SerializedName("wait_time")
    @Expose
    private Integer waitTime;
    @SerializedName("valid_until")
    @Expose
    private String validUntil;

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public Boolean getCaptchaUrl() {
        return captchaUrl;
    }

    public void setCaptchaUrl(Boolean captchaUrl) {
        this.captchaUrl = captchaUrl;
    }

    public Boolean getCaptchaW() {
        return captchaW;
    }

    public void setCaptchaW(Boolean captchaW) {
        this.captchaW = captchaW;
    }

    public Boolean getCaptchaH() {
        return captchaH;
    }

    public void setCaptchaH(Boolean captchaH) {
        this.captchaH = captchaH;
    }

    public Integer getWaitTime() {
        return waitTime;
    }

    public void setWaitTime(Integer waitTime) {
        this.waitTime = waitTime;
    }

    public String getValidUntil() {
        return validUntil;
    }

    public void setValidUntil(String validUntil) {
        this.validUntil = validUntil;
    }
}
