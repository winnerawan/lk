/*
 * Copyright (c) 2016. Wonderwall.biz.id | Winnerawan.net .
 * Coder @author Winnerawan T
 *
 */

package net.winnerawan.layarkaca.activity;

import java.io.Serializable;

/**
 * Created by kunal.bhatia on 06-05-2016.
 */
public interface HpLib_Listener extends Serializable {
    void player_created(VideoPlayer videoPlayer);
    //void click_next(Hplib_Tracker hplib_tracker);
    //void click_previous(Hplib_Tracker hplib_tracker);
    void click_cast();
}
