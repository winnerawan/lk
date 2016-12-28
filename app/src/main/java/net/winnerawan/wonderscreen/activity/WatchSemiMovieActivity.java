/*
 * Copyright (c) 2016. Wonderwall.biz.id | Winnerawan.net .
 * Coder @author Winnerawan T
 *
 */

package net.winnerawan.wonderscreen.activity;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.github.rtoshiro.view.video.FullscreenVideoLayout;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.universalvideoview.UniversalMediaController;

import net.winnerawan.wonderscreen.R;
import net.winnerawan.wonderscreen.helper.MyRequest;
import net.winnerawan.wonderscreen.model.Movie;
import net.winnerawan.wonderscreen.service.ApiService;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class WatchSemiMovieActivity extends AppCompatActivity {

    private static final String TAG = WatchSemiMovieActivity.class.getSimpleName();

    FullscreenVideoLayout videoView;
    UniversalMediaController mediaController;
    LinearLayout rytBuff;
    ProgressBar pBar;
    TextView txtTitle;
    TextView txtBufer;
    TextView txtTime;
    private int mSeekPosition;
    private int cachedHeight;
    private boolean isFullscreen;
    View mBottomLayout;
    View mVideoLayout;
    private static final String SEEK_POSITION_KEY = "SEEK_POSITION_KEY";

    InterstitialAd mInterstitialAd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getString(R.string.ad_unit_interstitial));

        AdRequest adRequest = new AdRequest.Builder()
                .build();

        // Load ads into Interstitial Ads
        mInterstitialAd.loadAd(adRequest);

        mInterstitialAd.setAdListener(new AdListener() {
            public void onAdLoaded() {
                showInterstitial();
            }
        });
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        //        WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_watch_movie);
        mVideoLayout = findViewById(R.id.activity_watch_movie);
        videoView = (FullscreenVideoLayout) findViewById(R.id.movie_view);
        //mediaController = (UniversalMediaController) findViewById(R.id.media_controller);
        txtBufer = (TextView) findViewById(R.id.txt_session);
        txtTitle = (TextView) findViewById(R.id.txt_title);
        txtTime = (TextView) findViewById(R.id.txt_sub_title);
        pBar = (ProgressBar) findViewById(R.id.loading);
        rytBuff = (LinearLayout) findViewById(R.id.lyt_buff);
        //videoView.setMediaController(mediaController);
        //videoView.setVideoViewCallback(this);
        Bundle bundle = getIntent().getExtras();
        int movie = bundle.getInt("movie_id");
        getMovie(movie);
        Log.e("TAG", "watch ---> "+movie);

    }

    private void showInterstitial() {
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }
    }


    private void getMovie(int id) {
        MyRequest request = new MyRequest();
        ApiService api = request.RequestMovie().create(ApiService.class);
        api.getSemiMovie(id, new Callback<Movie>() {
            @Override
            public void success(Movie movie, Response response) {
                //setVideoAreaSize();
                //videoView.setMediaController(mediaController);
                //Uri mov = Uri.parse(movie.getLink480().replaceAll("&amp;", "&"));
                Uri mov = Uri.parse(movie.getLink480());
                try{
                    videoView.setVideoURI(mov);
                }catch (Exception e) {
                    e.printStackTrace();
                }
                videoView.requestFocus();
                videoView.setShouldAutoplay(true);
                videoView.start();
                txtTime.setText(movie.getDuration());
                txtTitle.setText(movie.getTitle());
                txtBufer.setText("Buffering...");
                //mediaController.setTitle(movie.getTitle());
                videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mediaPlayer) {
                        mediaPlayer.setOnInfoListener(new MediaPlayer.OnInfoListener() {
                            @Override
                            public boolean onInfo(MediaPlayer mediaPlayer, int what, int extra) {

                                if (what == MediaPlayer.MEDIA_INFO_BUFFERING_START)
                                    rytBuff.setVisibility(View.VISIBLE);
                                    pBar.setVisibility(View.VISIBLE);
                                if (what == MediaPlayer.MEDIA_INFO_BUFFERING_END)
                                    rytBuff.setVisibility(View.GONE);
                                    pBar.setVisibility(View.GONE);
                                return false;
                            }
                        });
                    }
                });

                videoView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        if (rytBuff.getVisibility()==View.GONE) {
                            txtBufer.setText("Playing...");
                            rytBuff.setVisibility(View.VISIBLE);
                        } else {
                            rytBuff.setVisibility(View.GONE);
                        }
                        return false;
                    }
                });

            }

            @Override
            public void failure(RetrofitError error) {
                rytBuff.setVisibility(View.GONE);
                pBar.setVisibility(View.GONE);
            }
        });
    }

    private void setVideoAreaSize() {
        mVideoLayout.post(new Runnable() {
            @Override
            public void run() {
                int width = mVideoLayout.getWidth();
                cachedHeight = (int) (width * 405f / 720f);
//                cachedHeight = (int) (width * 3f / 4f);
//                cachedHeight = (int) (width * 9f / 16f);
                ViewGroup.LayoutParams videoLayoutParams = mVideoLayout.getLayoutParams();
                videoLayoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
                videoLayoutParams.height = cachedHeight;
                mVideoLayout.setLayoutParams(videoLayoutParams);
                //mVideoView.setVideoPath(VIDEO_URL);
                //mVideoView.requestFocus();
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d("TAG", "onSaveInstanceState Position=" + videoView.getCurrentPosition());
        outState.putInt(SEEK_POSITION_KEY, mSeekPosition);
    }

    @Override
    protected void onRestoreInstanceState(Bundle outState) {
        super.onRestoreInstanceState(outState);
        mSeekPosition = outState.getInt(SEEK_POSITION_KEY);
        Log.d("TAG", "onRestoreInstanceState Position=" + mSeekPosition);
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}
