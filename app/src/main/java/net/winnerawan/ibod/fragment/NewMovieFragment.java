package net.winnerawan.ibod.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.NativeExpressAdView;

import net.winnerawan.ibod.R;
import net.winnerawan.ibod.activity.DetailMovieActivity;
import net.winnerawan.ibod.adapter.RV_Adapter;
import net.winnerawan.ibod.helper.ItemClickSupport;
import net.winnerawan.ibod.helper.MyRequest;
import net.winnerawan.ibod.model.Movie;
import net.winnerawan.ibod.response.MovieResponse;
import net.winnerawan.ibod.service.ApiService;

import java.util.ArrayList;
import java.util.List;

import android.view.Window;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import retrofit.Callback;
import retrofit.RetrofitError;


public class NewMovieFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = NewMovieFragment.class.getSimpleName();

    private Dialog pDialog;
    private ProgressBar pBar;
    private List<Movie> movies;
    RecyclerView recyclerView;
    SwipeRefreshLayout swipeLayout;
    RV_Adapter adopter;
    public static final int ITEMS_PER_AD = 8;
    private List<Object> mRecyclerViewItems = new ArrayList<>() ;

    private static final int NATIVE_EXPRESS_AD_HEIGHT = 200;

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
        pBar.setVisibility(View.GONE);
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
      }


    private void getNewMovies() {
        MyRequest request = new MyRequest();
        ApiService api = request.RequestMovie().create(ApiService.class);
        api.getNewMovies(new Callback<MovieResponse>() {
            @Override
            public void success(MovieResponse movieResponse, retrofit.client.Response response) {
                pBar.setVisibility(View.GONE);
                swipeLayout.setRefreshing(false);
                boolean error = movieResponse.getError();
                if (!error) {
                    movies = movieResponse.getMovies();

                    int listSize =movies.size()+2;
                    int ITEM = 0;
                    int NATIVE_AD = 1;
                    int[] viewTypes = new int[listSize];
                    for (int i = 0; i < listSize; i++) {
                        //movies.add(new Movie());
                        //insert native ads once in five items
                        if (i > 1 && i % 3 == 0) {
                            viewTypes[i] = NATIVE_AD;
                        } else {
                            viewTypes[i] = ITEM;
                        }
                    }
                    //recyclerView.setAdapter(new MovieAdapter(movies, R.layout.adapter_home_conten, getActivity().getApplicationContext()));
                    Log.e(TAG, movieResponse.getError().toString());
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    recyclerView.setLayoutParams(params);
                    //adopter = new RV_Adapter(, viewTypes);
                    recyclerView.setAdapter(adopter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    //adopter.notifyDataSetChanged();

                    /*
                    mRecyclerViewItems.addAll(movies);
                    addNativeExpressAds();
                    setUpAndLoadNativeExpressAds();
                    RecyclerViewAdapter adapter = new RecyclerViewAdapter(getActivity(), mRecyclerViewItems);
                    recyclerView.setAdapter(adapter);
                    */
                    //recyclerView.setAdapter(new MovieAdapter(movies, R.layout.adapter_home_conten, getActivity().getApplicationContext()));

                }
            }

            @Override
            public void failure(RetrofitError error) {
                pBar.setVisibility(View.GONE);
                swipeLayout.setRefreshing(false);
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

    /**
     * Adds Native Express ads to the items list.
     */
    private void addNativeExpressAds() {

        // Loop through the items array and place a new Native Express ad in every ith position in
        // the items List.
        for (int i = 0; i <= mRecyclerViewItems.size(); i += ITEMS_PER_AD) {
            final NativeExpressAdView adView = new NativeExpressAdView(this.getActivity());
            mRecyclerViewItems.add(i, adView);
        }
    }

    /**
     * Sets up and loads the Native Express ads.
     */
    private void setUpAndLoadNativeExpressAds() {
        // Use a Runnable to ensure that the RecyclerView has been laid out before setting the
        // ad size for the Native Express ad. This allows us to set the Native Express ad's
        // width to match the full width of the RecyclerView.
        recyclerView.post(new Runnable() {
            @Override
            public void run() {
                final float density = getActivity().getResources().getDisplayMetrics().density;
                // Set the ad size and ad unit ID for each Native Express ad in the items list.
                for (int i = 0; i <= mRecyclerViewItems.size(); i += ITEMS_PER_AD) {
                    final NativeExpressAdView adView =
                            (NativeExpressAdView) mRecyclerViewItems.get(i);
                    AdSize adSize = new AdSize(
                            (int) (recyclerView.getWidth() / density),
                            NATIVE_EXPRESS_AD_HEIGHT);
                    adView.setAdSize(adSize);
                    adView.setAdUnitId(getResources().getString(R.string.ad_unit_id_native));
                }

                // Load the first Native Express ad in the items list.
                loadNativeExpressAd(0);
            }
        });
    }

    /**
     * Loads the Native Express ads in the items list.
     */
    private void loadNativeExpressAd(final int index) {

        if (index >= mRecyclerViewItems.size()) {
            return;
        }

        Object item = mRecyclerViewItems.get(index);
        if (!(item instanceof NativeExpressAdView)) {
            throw new ClassCastException("Expected item at index " + index + " to be a Native"
                    + " Express ad.");
        }

        final NativeExpressAdView adView = (NativeExpressAdView) item;

        // Set an AdListener on the NativeExpressAdView to wait for the previous Native Express ad
        // to finish loading before loading the next ad in the items list.
        adView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                // The previous Native Express ad loaded successfully, call this method again to
                // load the next ad in the items list.
                loadNativeExpressAd(index + ITEMS_PER_AD);
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // The previous Native Express ad failed to load. Call this method again to load
                // the next ad in the items list.
                Log.e("MainActivity", "The previous Native Express ad failed to load. Attempting to"
                        + " load the next Native Express ad in the items list.");
                loadNativeExpressAd(index + ITEMS_PER_AD);
            }
        });

        // Load the Native Express ad.
        adView.loadAd(new AdRequest.Builder().build());
    }

}
