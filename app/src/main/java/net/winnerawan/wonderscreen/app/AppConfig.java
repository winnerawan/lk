/*
 * Copyright (c) 2016. Wonderwall.biz.id | Winnerawan.net .
 * Coder @author Winnerawan T
 *
 */

package net.winnerawan.wonderscreen.app;

/**
 * Created by winnerawan on 12/8/16.
 */

public class AppConfig {

    /**
     * movie
     */

    public final static String YOUTUBE_API_KEY = "AIzaSyCA7RoaocAl86P2aBh3tkU3eGT1Uh-Tvbo";

    public final static String BASE_URL = "http://api.layarkaca.wonderwall.biz.id";

    public final static String SERVER_ONE = "http://lamoovie.com/dewa";

    public final static String URL_ALL_MOVIES = "";

    public final static String URL_NEW_MOVIES = "http://api.layarkaca.wonderwall.biz.id/v1/newMovies";

    public final static String URL_POPULER_MOVIES = "";

    /**
     * @serial movie
     */
    public final static String URL_SERIAL_MOVIES = "";

    public final static String URL_NEW_SERIAL_MOVIES = "";

    public final static String URL_POPULER_SERIAL_MOVIES = "";

    public static final String RESTRICTED_CONTENT = "http://api.layarkaca.wonderwall.biz.id/v1/restricted.png";

    // global topic to receive app wide push notifications
    public static final String TOPIC_GLOBAL = "global";

    // broadcast receiver intent filters
    public static final String REGISTRATION_COMPLETE = "registrationComplete";
    public static final String PUSH_NOTIFICATION = "pushNotification";

    // id to handle the notification in the notification tray
    public static final int NOTIFICATION_ID = 100;
    public static final int NOTIFICATION_ID_BIG_IMAGE = 101;

    public static final String SHARED_PREF = "ah_firebase";


}
