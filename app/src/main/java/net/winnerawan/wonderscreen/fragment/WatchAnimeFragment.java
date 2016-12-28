/*
 * Copyright (c) 2016. Wonderwall.biz.id | Winnerawan.net .
 * Coder @author Winnerawan T
 *
 */

package net.winnerawan.wonderscreen.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import net.winnerawan.wonderscreen.R;
import net.winnerawan.wonderscreen.activity.WatchAnimeActivity;
import net.winnerawan.wonderscreen.activity.WatchSerialActivity;
import net.winnerawan.wonderscreen.adapter.EpisodeAdapter;
import net.winnerawan.wonderscreen.app.AppController;
import net.winnerawan.wonderscreen.helper.ItemClickSupport;
import net.winnerawan.wonderscreen.helper.MyRequest;
import net.winnerawan.wonderscreen.model.Episode;
import net.winnerawan.wonderscreen.response.EpisodeResponse;
import net.winnerawan.wonderscreen.service.ApiService;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by winnerawan on 12/15/16.
 */

public class WatchAnimeFragment extends Fragment {

    private static final String TAG = WatchAnimeFragment.class.getSimpleName();

    private ImageLoader imageLoader = AppController.getInstance().getImageLoader();
    private NetworkImageView networkImageView;
    private ProgressBar pBar;
    private TextView txtTitle;
    private TextView txtRating;
    private TextView txtSeason;
    private RecyclerView recycleEpisode;
    private List<Episode> episodes;

    public WatchAnimeFragment() {

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_program_episode, container, false);
        initLayout(view);
        Bundle bundle = getActivity().getIntent().getExtras();
        final int id = bundle.getInt("id");
        Log.e(TAG, "serial --> "+id);
        getEpisode(id);
        return view;
    }

    private void getEpisode(int id) {
        MyRequest req = new MyRequest();
        ApiService api = req.RequestMovie().create(ApiService.class);
        api.getFullAnime(id, new Callback<EpisodeResponse>() {
            @Override
            public void success(EpisodeResponse episodeResponse, Response response) {
                pBar.setVisibility(View.GONE);
                boolean err = episodeResponse.getError();
                if (!err) {
                    networkImageView.setImageUrl(episodeResponse.getImage(), imageLoader);
                    txtSeason.setText("Season"+episodeResponse.getSeason());
                    txtRating.setText(String.valueOf(episodeResponse.getImdbRating()));
                    txtTitle.setText(episodeResponse.getTitle());
                    episodes = episodeResponse.getListEpisode();
                    recycleEpisode.setAdapter(new EpisodeAdapter(episodes, R.layout.adapter_program_episode, getActivity().getApplicationContext()));
                }
            }

            @Override
            public void failure(RetrofitError error) {
                pBar.setVisibility(View.GONE);
            }
        });

        ItemClickSupport.addTo(recycleEpisode).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                int episode = episodes.get(position).getEpisode();
                int serial_id = episodes.get(position).getSerialId();
                Intent i = new Intent(getActivity(), WatchAnimeActivity.class);
                i.putExtra("serial_id", serial_id);
                i.putExtra("episode", episode);
                startActivity(i);
            }
        });

    }

    private void initLayout(View view) {
        this.pBar = (ProgressBar) view.findViewById(R.id.loading2);
        this.networkImageView = (NetworkImageView) view.findViewById(R.id.img_content);
        this.txtTitle = (TextView) view.findViewById(R.id.txt_title);
        this.txtRating = (TextView) view.findViewById(R.id.txt_rating);
        this.txtSeason = (TextView) view.findViewById(R.id.txt_season);
        this.recycleEpisode = (RecyclerView) view.findViewById(R.id.recyclerview_list_episode);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recycleEpisode.setLayoutManager(layoutManager);
    }
}
