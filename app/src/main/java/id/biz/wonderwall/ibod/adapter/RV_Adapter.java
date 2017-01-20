/*
 * Copyright (c) 2016. Wonderwall.biz.id | Winnerawan.net .
 * Coder @author Winnerawan T
 *
 */

package id.biz.wonderwall.ibod.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.NativeExpressAdView;
import com.imagezoom.ImageViewTouch;


import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import id.biz.wonderwall.ibod.R;
import id.biz.wonderwall.ibod.app.AppConfig;
import id.biz.wonderwall.ibod.app.AppInterface;
import id.biz.wonderwall.ibod.app.AppRequest;
import id.biz.wonderwall.ibod.model.File;
import id.biz.wonderwall.ibod.response.ThumbnailResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by winnerawan on 12/16/16.
 */

public class RV_Adapter extends RecyclerView.Adapter<ViewHolder> {

    private static final int ITEM = 0;
    private static final int NATIVE_AD = 1;
    private Context context;
    String screenshot ="https://thumb.oloadcdn.net/splash/woNA_6uDeKQ/6g0dZQcEvyk.jpg";

    int[] viewTypes;
    List<File> files = Collections.emptyList();
    List<ThumbnailResponse> thumbnails = Collections.emptyList();

    public RV_Adapter(List<File> files, int[] viewTypes) {
        this.files = files;
        //this.thumbnails=thumbnails;
        this.viewTypes = viewTypes;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Inflate the layout, initialize the View Holder
        context = parent.getContext();
        View v;
        if (viewType == ITEM) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_home_conten, parent, false);
            ViewHolder holder = new ItemViewHolder(v);
            return holder;
        } else if (viewType == NATIVE_AD) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.re_ads, parent, false);
            ViewHolder holder = new AdViewHolder(v);
            return holder;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int position) {
        if (viewHolder.getItemViewType() == ITEM) {
            final ItemViewHolder holder = (ItemViewHolder) viewHolder;
            //populate the RecyclerView
            //holder.title.setText(list.get(position).getTitle());
            //holder.description.setText(list.get(position).getDescription());
            holder.txtTitle.setText(files.get(position).getName());
            //holder.imageView.setImageUrl(movies.get(position).getImage(), imageLoader);
            final AppRequest req = new AppRequest();
            final AppInterface api = req.Request().create(AppInterface.class);
            //api.getThumbnail(AppConfig.LOGIN, AppConfig.KEY, files.get(position).getLinkextid());
            final Call<ThumbnailResponse> thumb = api.getThumbnail(AppConfig.LOGIN, AppConfig.KEY, files.get(position).getLinkextid());
            thumb.enqueue(new Callback<ThumbnailResponse>() {
                @Override
                public void onResponse(Call<ThumbnailResponse> call, Response<ThumbnailResponse> response) {
                    Glide.with(context).load(Uri.parse(response.body().getResult())).thumbnail(0.5F).crossFade().diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.imageView);
                    if (response.body().getResult()==null) {

                    }
                }

                @Override
                public void onFailure(Call<ThumbnailResponse> call, Throwable t) {
                    Log.e("TAG", "THUMB"+t.getMessage());
                }
            });
            //holder.txtQuality.setText(movies.get(position).getDuration());
            //Glide.with(context).load(Uri.parse(thumbnails.get(position).getResult())).thumbnail(0.5F).crossFade().diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.imageView);
            long size = Long.parseLong(files.get(position).getSize());
            String size_fixed = android.text.format.Formatter.formatFileSize(context, size);
            holder.txtSubTitle.setText(size_fixed);
        } else if (viewHolder.getItemViewType() == NATIVE_AD) {
            AdViewHolder holder = (AdViewHolder) viewHolder;

            //Load the Ad
            AdRequest request = new AdRequest.Builder()
                    .build();
            holder.adView.loadAd(request);
        }
    }

    @Override
    public int getItemCount() {
        return files.size();
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

class ViewHolder extends RecyclerView.ViewHolder {
    public ViewHolder(View v) {
        super(v);
    }
}

class ItemViewHolder extends ViewHolder {

    TextView txtTitle;
    TextView txtSubTitle;
    TextView txtQuality;
    ImageView imageView;

    ItemViewHolder(View itemView) {
        super(itemView);
        txtTitle = (TextView) itemView.findViewById(R.id.txt_title);
        txtSubTitle = (TextView) itemView.findViewById(R.id.txt_episode);
        txtQuality = (TextView) itemView.findViewById(R.id.txt_sub_title);
        imageView = (ImageView) itemView.findViewById(R.id.img_content);
    }
}

class AdViewHolder extends ViewHolder {
    NativeExpressAdView adView;

    public AdViewHolder(View v) {
        super(v);
        adView = (NativeExpressAdView) v.findViewById(R.id.nativeAdsView);
    }
}
