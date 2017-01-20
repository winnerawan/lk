package id.biz.wonderwall.ibod.model;

/**
 * Created by winnerawan on 1/19/17.
 */

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FileResult {

    @SerializedName("folders")
    @Expose
    private List<Object> folders = null;
    @SerializedName("files")
    @Expose
    private List<File> files = null;

    public List<Object> getFolders() {
        return folders;
    }

    public void setFolders(List<Object> folders) {
        this.folders = folders;
    }

    public List<File> getFiles() {
        return files;
    }

    public void setFiles(List<File> files) {
        this.files = files;
    }
}
