package id.biz.wonderwall.ibod.app;

import id.biz.wonderwall.ibod.response.AccountResponse;
import id.biz.wonderwall.ibod.response.ListFolderResponse;
import id.biz.wonderwall.ibod.response.ThumbnailResponse;
import id.biz.wonderwall.ibod.response.TicketResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by winnerawan on 1/19/17.
 */

public interface AppInterface {

    /**
     * Everything account related (total used storage, reward, ...)
     * @param login
     * @param key
     * @return extid, email, signup_at, storage_left, storage_used, traffic, balance
     */
    @GET("/1/account/info")
    Call<AccountResponse> getAccount(@Query("login") String login, @Query("key") String key);

    /**
     * Shows the content of folders
     * @param login
     * @param key
     * @param folder
     * @return name, sha1, folderid, upload_at, status, size, content_type, download_count, cstatus, link, linkextid
     */
    @GET("/1/file/listfolder")
    Call<ListFolderResponse> listFolder(@Query("login") String login, @Query("key") String key, @Query("folder") int folder);

    /**
     * Shows the video splash image (thumbnail)
     * @param login
     * @param key
     * @param file
     * @return url.com/image.jpg
     */
    @GET("/1/file/getsplash")
    Call<ThumbnailResponse> getThumbnail(@Query("login") String login, @Query("key") String key, @Query("file") String file);
    /**
     * Preparing a Download
     * @param file
     * @param login
     * @param key
     * @return ticket, captcha_url, captcha_w, captcha_h, wait_time, valid_until
     */
    @GET("/1/file/dlticket")
    Call<TicketResponse> getTicket(@Query("file") String file, @Query("login") String login, @Query("key") String key);





}
