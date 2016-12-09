package net.winnerawan.layarkaca.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;

import net.winnerawan.layarkaca.R;
import net.winnerawan.layarkaca.adapter.ListMovieAdapter;
import net.winnerawan.layarkaca.app.AppConfig;
import net.winnerawan.layarkaca.app.AppController;
import net.winnerawan.layarkaca.helper.MyRequest;
import net.winnerawan.layarkaca.model.Movie;
import net.winnerawan.layarkaca.response.MovieResponse;
import net.winnerawan.layarkaca.service.ApiService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import android.widget.ListView;

import retrofit.Callback;
import retrofit.RetrofitError;


public class NewMovieFragment extends ListFragment {

    private static final String TAG = NewMovieFragment.class.getSimpleName();

    private JSONObject obj;
    private ProgressDialog pDialog;
    private List<Movie> listMovie = new ArrayList<Movie>();
    private ListMovieAdapter adapter;
    ListView ListView;

    public NewMovieFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.list, container, false);
        ListView = (ListView) view.findViewById(android.R.id.list);
        adapter = new ListMovieAdapter(this, listMovie);
        ListView.setAdapter(adapter);
        listMovie.clear();
        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Loading...");
        pDialog.show();

        getNewMovies();
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
                boolean error = movieResponse.getError();
                if (!error) {
                    listMovie = movieResponse.getMovies();
                    Log.e(TAG, movieResponse.getError().toString());
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e(TAG, error.getMessage());
            }
        });
    }

}
