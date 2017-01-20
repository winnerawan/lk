package id.biz.wonderwall.ibod.response;

/**
 * Created by winnerawan on 1/19/17.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import id.biz.wonderwall.ibod.model.FileResult;

public class ListFolderResponse {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("result")
    @Expose
    private FileResult result;

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

    public FileResult getResult() {
        return result;
    }

    public void setResult(FileResult result) {
        this.result = result;
    }
}
