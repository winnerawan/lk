package net.winnerawan.layarkaca.helper;

import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.models.User;

import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by winnerawan on 12/7/16.
 */

public class MyTwitterApiClient extends TwitterApiClient {
    public MyTwitterApiClient(TwitterSession session) {
        super(session);
    }

    public UsersService getUsersService() {
        return getService(UsersService.class);
    }
}

interface UsersService {
    @GET("/1.1/users/show.json")
    void show(@Query("user_id") Long userId,
              @Query("screen_name") String screenName,
              @Query("include_entities") Boolean includeEntities,
              Callback<User> cb);
}