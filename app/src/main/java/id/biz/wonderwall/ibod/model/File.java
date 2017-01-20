package id.biz.wonderwall.ibod.model;

/**
 * Created by winnerawan on 1/19/17.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class File {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("cblock")
    @Expose
    private Object cblock;
    @SerializedName("sha1")
    @Expose
    private String sha1;
    @SerializedName("folderid")
    @Expose
    private String folderid;
    @SerializedName("upload_at")
    @Expose
    private String uploadAt;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("size")
    @Expose
    private String size;
    @SerializedName("content_type")
    @Expose
    private String contentType;
    @SerializedName("download_count")
    @Expose
    private String downloadCount;
    @SerializedName("cstatus")
    @Expose
    private String cstatus;
    @SerializedName("link")
    @Expose
    private String link;
    @SerializedName("linkextid")
    @Expose
    private String linkextid;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getCblock() {
        return cblock;
    }

    public void setCblock(Object cblock) {
        this.cblock = cblock;
    }

    public String getSha1() {
        return sha1;
    }

    public void setSha1(String sha1) {
        this.sha1 = sha1;
    }

    public String getFolderid() {
        return folderid;
    }

    public void setFolderid(String folderid) {
        this.folderid = folderid;
    }

    public String getUploadAt() {
        return uploadAt;
    }

    public void setUploadAt(String uploadAt) {
        this.uploadAt = uploadAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getDownloadCount() {
        return downloadCount;
    }

    public void setDownloadCount(String downloadCount) {
        this.downloadCount = downloadCount;
    }

    public String getCstatus() {
        return cstatus;
    }

    public void setCstatus(String cstatus) {
        this.cstatus = cstatus;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getLinkextid() {
        return linkextid;
    }

    public void setLinkextid(String linkextid) {
        this.linkextid = linkextid;
    }

}
