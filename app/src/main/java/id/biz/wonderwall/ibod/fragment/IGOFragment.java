package id.biz.wonderwall.ibod.fragment;

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
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.NativeExpressAdView;

import java.util.ArrayList;
import java.util.List;

import id.biz.wonderwall.ibod.R;
import id.biz.wonderwall.ibod.adapter.MovieAdapter;
import id.biz.wonderwall.ibod.adapter.RV_Adapter;
import id.biz.wonderwall.ibod.app.AppConfig;
import id.biz.wonderwall.ibod.app.AppInterface;
import id.biz.wonderwall.ibod.app.AppRequest;
import id.biz.wonderwall.ibod.helper.ItemClickSupport;
import id.biz.wonderwall.ibod.model.File;
import id.biz.wonderwall.ibod.response.ListFolderResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class IGOFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = IGOFragment.class.getSimpleName();

    private Dialog pDialog;
    private ProgressBar pBar;
    private List<File> files;
    RecyclerView recyclerView;
    SwipeRefreshLayout swipeLayout;
    RV_Adapter adopter;
    public static final int ITEMS_PER_AD = 8;
    private List<Object> mRecyclerViewItems = new ArrayList<>() ;

    private static final int NATIVE_EXPRESS_AD_HEIGHT = 200;

    InterstitialAd mInterstitialAd;

    public IGOFragment() {
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

        prepareAds();

        newIndo(AppConfig.LOGIN, AppConfig.KEY, AppConfig.DIRECTORY_IGO);

        swipeLayout.post(new Runnable() {
                              @Override
                              public void run() {
                                  swipeLayout.setRefreshing(true);
                                  newIndo(AppConfig.LOGIN, AppConfig.KEY, AppConfig.DIRECTORY_IGO);
                              }
                          }
        );

          return view;
      }

      @Override
      public void onDestroy() {
          super.onDestroy();
      }


    private void newIndo(final String login, final String key, final int folder) {
        AppRequest request = new AppRequest();
        AppInterface api = request.Request().create(AppInterface.class);
        Call<ListFolderResponse> list = api.listFolder(login, key, folder);
        list.enqueue(new Callback<ListFolderResponse>() {
            @Override
            public void onResponse(Call<ListFolderResponse> call, Response<ListFolderResponse> response) {
                pBar.setVisibility(View.GONE);
                swipeLayout.setRefreshing(false);
                files = response.body().getResult().getFiles();
                Log.e(TAG, "SUCCESS -->" + response.body().getStatus());
                /*
                    int listSize =files.size();
                    int ITEM = 0;
                    int NATIVE_AD = 1;
                    int[] viewTypes = new int[listSize];
                    for (int i = 0; i < listSize; i++) {
                        //movies.add(new Movie());
                        //insert native ads once in five items
                        if (i > 1 && i % 5 == 0) {
                            viewTypes[i] = NATIVE_AD;
                        } else {
                            viewTypes[i] = ITEM;
                        }
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        recyclerView.setLayoutParams(params);
                        adopter = new RV_Adapter(files, viewTypes);
                        recyclerView.setAdapter(adopter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                        */
                recyclerView.setAdapter(new MovieAdapter(files, R.layout.adapter_home_conten, getActivity().getApplicationContext()));

                //}
            }

            @Override
            public void onFailure(Call<ListFolderResponse> call, Throwable t) {
                Log.e(TAG, "ERROR -->"+t.getMessage());
            }
        });

        /*
        api.newIndo(new Callback<MoviesResponse>() {
            @Override
            public void success(MoviesResponse movieResponse, retrofit.client.Response response) {
                pBar.setVisibility(View.GONE);
                swipeLayout.setRefreshing(false);
                boolean error = movieResponse.getError();
                if (!error) {
                    movies = movieResponse.getMovies();
                    Log.e(TAG, "success---> "+movies.get(1).getTitle() +" -->"+movieResponse.getError());

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
                    adopter = new RV_Adapter(movies, viewTypes);
                    recyclerView.setAdapter(adopter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    //adopter.notifyDataSetChanged();


                }
            }
            */
            /**
            @Override
            public void failure(RetrofitError error) {
                pBar.setVisibility(View.GONE);
                swipeLayout.setRefreshing(false);
                Log.e(TAG, "error "+error.getMessage());
            }
        });
        */

        /*

        ItemClickSupport.addTo(recyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                Intent i = new Intent(getActivity().getApplicationContext(), DetailActivity.class);
                i.putExtra("id", movies.get(position).getId());
                i.putExtra("title", movies.get(position).getTitle());
                i.putExtra("image", movies.get(position).getImage());
                i.putExtra("link", movies.get(position).getLink());
                mInterstitialAd.setAdListener(new AdListener() {
                    public void onAdLoaded() {
                        showInterstitial();
                    }
                });
                startActivity(i);

            }
        });
        */
    }

    @Override
    public void onRefresh() {
        pBar.setVisibility(View.VISIBLE);
        newIndo(AppConfig.LOGIN, AppConfig.KEY, AppConfig.DIRECTORY_IGO);
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
                    adView.setAdUnitId(getResources().getString(R.string.ads_native));
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
    private void prepareAds() {
        mInterstitialAd = new InterstitialAd(getActivity().getApplicationContext());

        // set the ad unit ID
        mInterstitialAd.setAdUnitId(getString(R.string.ads_interstitial));

        AdRequest adRequest = new AdRequest.Builder()
                .build();

        // Load ads into Interstitial Ads
        mInterstitialAd.loadAd(adRequest);


    }

    private void showInterstitial() {
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }
    }


}
