package id.biz.wonderwall.ibod.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

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
 * Created by winnerawan on 12/10/16.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    public List<File> files;
    private int rowLayout;
    private Context context;
    int AD_TYPE = 0;
    int CONTENT_TYPE = 1;

    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout movieLayout;
        TextView txtTitle;
        TextView txtSubTitle;
        TextView txtQuality;
        ImageView imageView;

        public MovieViewHolder(View v) {
            super(v);
            movieLayout = (RelativeLayout) v.findViewById(R.id.lyt_container_home_adapter);
            txtTitle = (TextView) v.findViewById(R.id.txt_title);
            txtSubTitle = (TextView) v.findViewById(R.id.txt_sub_title);
            txtQuality = (TextView) v.findViewById(R.id.txt_episode);
            imageView = (ImageView) v.findViewById(R.id.img_content);

        }
    }

    public MovieAdapter(List<File> files, int rowLayout, Context context) {
        this.files=files;
        this.rowLayout=rowLayout;
        this.context=context;

    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new MovieViewHolder(view);
        //return view;
    }

    @Override
    public void onBindViewHolder(final MovieViewHolder holder, int position) {

        AppRequest request = new AppRequest();
        AppInterface api = request.Request().create(AppInterface.class);
        Call<ThumbnailResponse> thumb = api.getThumbnail(AppConfig.LOGIN, AppConfig.KEY, files.get(position).getLinkextid());
        thumb.enqueue(new Callback<ThumbnailResponse>() {
            @Override
            public void onResponse(Call<ThumbnailResponse> call, Response<ThumbnailResponse> response) {
                if (response.body().getResult().equals("")|| response.body().getResult()==null) {
                    String screenshot ="https://thumb.oloadcdn.net/splash/woNA_6uDeKQ/6g0dZQcEvyk.jpg";
                    Glide.with(context).load(Uri.parse(screenshot)).diskCacheStrategy(DiskCacheStrategy.ALL).crossFade().thumbnail(0.5F).into(holder.imageView);
                } else {
                    Glide.with(context).load(Uri.parse(response.body().getResult())).diskCacheStrategy(DiskCacheStrategy.ALL).crossFade().thumbnail(0.5F).into(holder.imageView);
                }
            }

            @Override
            public void onFailure(Call<ThumbnailResponse> call, Throwable t) {
                Log.e("TAG", "thumb error "+t.getMessage());
            }
        });
        holder.txtTitle.setText(files.get(position).getName());
        long size = Long.parseLong(files.get(position).getSize());
        String size_fixed = android.text.format.Formatter.formatFileSize(context, size);
        holder.txtSubTitle.setText(size_fixed);
        //holder.txtQuality.setText(movies.get(position).getQuality());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        holder.movieLayout.setLayoutParams(params);


    }

    @Override
    public int getItemCount() {
        return files.size();
    }

    public void setFilter(List<File> moviesList) {
        moviesList.addAll(files);
        notifyDataSetChanged();
    }

    public Object getItem(int location) {
        return files.get(location);
    }

}