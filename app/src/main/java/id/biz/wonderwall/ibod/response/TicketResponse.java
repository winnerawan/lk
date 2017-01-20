package id.biz.wonderwall.ibod.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import id.biz.wonderwall.ibod.model.TicketResult;

/**
 * Created by winnerawan on 1/19/17.
 */

public class TicketResponse {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("result")
    @Expose
    private TicketResult result;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public TicketResult getResult() {
        return result;
    }

    public void setResult(TicketResult result) {
        this.result = result;
    }
}