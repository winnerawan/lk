/*
 * Copyright (c) 2016. Wonderwall.biz.id | Winnerawan.net .
 * Coder @author Winnerawan T
 *
 */

package net.winnerawan.wonderscreen.adapter;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.NativeExpressAdView;

import net.winnerawan.wonderscreen.R;
import net.winnerawan.wonderscreen.app.AppController;
import net.winnerawan.wonderscreen.helper.SQLiteHandler;
import net.winnerawan.wonderscreen.model.Episode;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * Created by winnerawan on 12/19/16.
 */

public class RV_EpisodeAdapter extends RecyclerView.Adapter<EpisodeViewHolder> {

    private static final int ITEM = 0;
    private static final int NATIVE_AD = 1;

    SQLiteHandler db;
    int[] viewTypes;
    String AGE;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    List<Episode> list = Collections.emptyList();

    public RV_EpisodeAdapter(List<Episode> list, int[] viewTypes) {
        this.list = list;
        this.viewTypes = viewTypes;
    }

    @Override
    public EpisodeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Inflate the layout, initialize the View Holder
        db = new SQLiteHandler(parent.getContext().getApplicationContext());
        HashMap<String, String> user = db.getUserDetails();
        AGE = user.get("age");
        View v;
        if (viewType == ITEM) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_program_episode, parent, false);
            EpisodeViewHolder holder = new ItemEpisodeViewHolder(v);
            return holder;
        } else if (viewType == NATIVE_AD) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.re_ads_episode, parent, false);
            EpisodeViewHolder holder = new AdEpisodeViewHolder(v);
            return holder;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(EpisodeViewHolder viewHolder, final int position) {
        if (viewHolder.getItemViewType() == ITEM) {
            ItemEpisodeViewHolder holder = (ItemEpisodeViewHolder) viewHolder;
            //populate the RecyclerView
            //holder.title.setText(list.get(position).getTitle());
            //holder.description.setText(list.get(position).getDescription());
            holder.imageView.setImageUrl(list.get(position).getThumbnail(), imageLoader);
            holder.txtTitle.setText(list.get(position).getSubTitle());
            holder.txtEpisode.setText("Episode "+list.get(position).getEpisode());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            holder.dlImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(list.get(position).getLink()));
                    view.getContext().startActivity(i);
                    //Toast.makeText("Toast!!", )
                }
            });
        } else if (viewHolder.getItemViewType() == NATIVE_AD) {
            AdEpisodeViewHolder holder = (AdEpisodeViewHolder) viewHolder;

            //Load the Ad
            AdRequest request = new AdRequest.Builder()
                    .build();
            holder.adView.loadAd(request);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemViewType(int position) {
        return viewTypes[position];
    }
}

class EpisodeViewHolder extends RecyclerView.ViewHolder {
    public EpisodeViewHolder(View v) {
        super(v);
    }
}

class ItemEpisodeViewHolder extends EpisodeViewHolder {

    TextView txtTitle;
    TextView txtEpisode;
    NetworkImageView imageView;
    ImageView dlImg;

    ItemEpisodeViewHolder(View itemView) {
        super(itemView);
        txtTitle = (TextView) itemView.findViewById(R.id.txt_title);
        txtEpisode = (TextView) itemView.findViewById(R.id.txt_episode);
        imageView = (NetworkImageView) itemView.findViewById(R.id.img_content);
        dlImg = (ImageView) itemView.findViewById(R.id.img_add_to_my_list);
    }
}

class AdEpisodeViewHolder extends EpisodeViewHolder {
    NativeExpressAdView adView;

    public AdEpisodeViewHolder(View v) {
        super(v);
        adView = (NativeExpressAdView) v.findViewById(R.id.nativeAdsViewSmall);
    }
}