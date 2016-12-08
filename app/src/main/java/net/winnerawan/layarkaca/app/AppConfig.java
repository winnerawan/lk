/*
 * Copyright (c) 2016. Wonderwall.biz.id | Winnerawan.net .
 * Coder @author Winnerawan T
 *
 */

package net.winnerawan.layarkaca.app;

/**
 * Created by winnerawan on 12/8/16.
 */

public class AppConfig {

    /**
     * @see
     */
    public final static String URL_ALL_MOVIES = "";

    public final static String URL_NEW_MOVIES = "";

    public final static String URL_POPULER_MOVIES = "";

    /**
     * @serial movie
     */
    public final static String URL_SERIAL_MOVIES = "";

    public final static String URL_NEW_SERIAL_MOVIES = "";

    public final static String URL_POPULER_SERIAL_MOVIES = "";

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