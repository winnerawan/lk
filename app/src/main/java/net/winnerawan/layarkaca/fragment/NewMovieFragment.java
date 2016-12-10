package net.winnerawan.layarkaca.fragment;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;

import net.winnerawan.layarkaca.R;
import net.winnerawan.layarkaca.activity.DetailMovieActivity;
import net.winnerawan.layarkaca.adapter.ListMovieAdapter;
import net.winnerawan.layarkaca.adapter.MovieAdapter;
import net.winnerawan.layarkaca.app.AppConfig;
import net.winnerawan.layarkaca.app.AppController;
import net.winnerawan.layarkaca.helper.ItemClickSupport;
import net.winnerawan.layarkaca.helper.MyRequest;
import net.winnerawan.layarkaca.model.Movie;
import net.winnerawan.layarkaca.response.MovieResponse;
import net.winnerawan.layarkaca.service.ApiService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import android.view.Window;
import android.widget.ListView;
import android.widget.ProgressBar;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RetrofitError;


public class NewMovieFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = NewMovieFragment.class.getSimpleName();

    private Dialog pDialog;
    private ProgressBar pBar;
    private List<Movie> movies;
    RecyclerView recyclerView;
    SwipeRefreshLayout swipeLayout;

    public NewMovieFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_home);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getActivity());
        swipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe);
        recyclerView.setLayoutManager(layoutManager);
        pDialog = new Dialog(this.getActivity());
        pDialog.setCancelable(false);
        pDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        swipeLayout.setColorSchemeResources(R.color.colorAccent);

        pBar = (ProgressBar) view.findViewById(R.id.loading);
        swipeLayout.setOnRefreshListener(this);

        getNewMovies();

        swipeLayout.post(new Runnable() {
                              @Override
                              public void run() {
                                  swipeLayout.setRefreshing(true);
                                  getNewMovies();
                              }
                          }
        );
/*
        JsonArrayRequest movieRequest = new JsonArrayRequest(AppConfig.URL_NEW_MOVIES, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.e(TAG, response.toString());
                hidePDialog();
                for (int i = 0; i < response.length(); i++) {
                    try {
                        obj = response.getJSONObject(i);
                        Movie movie = new Movie();
                        movie.setId(obj.getInt("id"));
                        movie.setTitle(obj.getString("title"));
                        movie.setImage(obj.getString("image"));
                        movie.setGenre(obj.getString("genre"));
                        movie.setQuality(obj.getString("quality"));

                        listMovie.add(movie);
                      } catch (JSONException e) {
                          e.printStackTrace();
                      }
                  }
                  adapter.notifyDataSetChanged();
              }
          }, new Response.ErrorListener() {
              @Override
              public void onErrorResponse(VolleyError error) {
                  VolleyLog.d(TAG, "Error: " + error.getMessage());
                  VolleyLog.e("Error: ", ""+error.toString());
                  hidePDialog();
              }
          });
          AppController.getInstance().addToRequestQueue(movieRequest);
          */
          return view;
      }

      @Override
      public void onDestroy() {
          super.onDestroy();
          hidePDialog();
      }

      private void hidePDialog() {
          if (pDialog != null) {
              pDialog.dismiss();
              pDialog = null;
          }
    }

    private void getNewMovies() {
        MyRequest request = new MyRequest();
        ApiService api = request.RequestMovie().create(ApiService.class);
        api.getNewMovies(new Callback<MovieResponse>() {
            @Override
            public void success(MovieResponse movieResponse, retrofit.client.Response response) {
                pDialog.dismiss();
                pBar.setVisibility(View.GONE);
                swipeLayout.setRefreshing(false);
                boolean error = movieResponse.getError();
                if (!error) {
                    movies = movieResponse.getMovies();
                    recyclerView.setAdapter(new MovieAdapter(movies, R.layout.adapter_home_conten, getActivity().getApplicationContext()));
                    Log.e(TAG, movieResponse.getError().toString());
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e(TAG, "error "+error.getMessage());
            }
        });

        ItemClickSupport.addTo(recyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                Intent i = new Intent(getActivity().getApplicationContext(), DetailMovieActivity.class);
                i.putExtra("id", movies.get(position).getId());
                i.putExtra("title", movies.get(position).getTitle());
                i.putExtra("image", movies.get(position).getImage());
                startActivity(i);
            }
        });
    }

    @Override
    public void onRefresh() {
        pBar.setVisibility(View.VISIBLE);
        getNewMovies();
    }

}
