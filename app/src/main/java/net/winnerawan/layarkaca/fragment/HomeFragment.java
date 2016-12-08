package net.winnerawan.layarkaca.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.analytics.HitBuilders.ScreenViewBuilder;
import com.google.android.gms.analytics.Tracker;
import org.json.JSONObject;

import net.winnerawan.layarkaca.R;
import net.winnerawan.layarkaca.adapter.HomeAdapter;
import net.winnerawan.layarkaca.app.AppConfig;
import net.winnerawan.layarkaca.model.HomePage;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by winnerawan on 12/8/16.
 */

public class HomeFragment extends Fragment {

    private HomeAdapter homeAdapter;
    private HomePage homePage;
    private ProgressBar loading;
    private RequestQueue queue;
    private RecyclerView recyclerview_home;
    private SwipeRefreshLayout swipe;

    private static int MY_SOCKET_TIMEOUT_MS = 200000;

    private void initAction() {
        this.swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //HomeFragment.this.ref
            }
        });
    }

    /*
    private void refreshLayout() {
        JsonObjectRequest localJsonObjectRequest = new JsonObjectRequest(0, AppConfig.URL_NEW_MOVIES, null, new Response.Listener()new Response.ErrorListener
        {
            public void onResponse(JSONObject paramAnonymousJSONObject)
            {
                HomeFragment.access$102(HomeFragment.this, HomePage.getHomePageFromJson(paramAnonymousJSONObject));
                HomeFragment.this.homeAdapter.refresh(HomeFragment.this.homePage);
                HomeFragment.this.swipe.setRefreshing(false);
            }
        }, new Response.ErrorListener()
    {
        public void onErrorResponse(VolleyError paramAnonymousVolleyError)
        {
            HomeFragment.this.loading.setVisibility(8);
            HomeFragment.this.swipe.setRefreshing(false);
        }
    });
        localJsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(MY_SOCKET_TIMEOUT_MS, 1, 1.0F));
        this.queue.add(localJsonObjectRequest);

    }
    */



    private void refreshLayout() {

        JsonObjectRequest request = new JsonObjectRequest(0, AppConfig.URL_NEW_MOVIES, new JSONObject(), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //HomeFragment.instantiate(HomeFragment.this, HomeFragment.get)
                //HomeFragment.this.homeAdapter.refresh(HomeFragment.this.homePage);
                HomeFragment.this.swipe.setRefreshing(false);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                HomeFragment.this.loading.setVisibility(View.GONE);
                HomeFragment.this.swipe.setRefreshing(false);
            }
        });

        request.setRetryPolicy(new DefaultRetryPolicy(MY_SOCKET_TIMEOUT_MS, 1, 1.0F));
        this.queue.add(request);
    }

    private void getDataMovies() {
        JsonObjectRequest request = new JsonObjectRequest(0, AppConfig.URL_POPULER_MOVIES, new JSONObject(), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //HomeFragment.this.ini
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // on error TODO
            }
        });
    }

    private void initAdapter() {
        //this.homeAdapter = new HomeAdapter(this.homePage, getActivity(), getFragmentManager());
        //this.recyclerview_home.setAdapter(this.homeAdapter);
        this.homeAdapter.notifyDataSetChanged();
        this.swipe.setRefreshing(false);
        this.loading.setVisibility(View.GONE);
    }
/*
    private void initLayoutParam(View paramView) {
        paramView = new LinearLayoutManager(paramView.getContext());
        paramView.setRe
        this.recyclerview_home.setLayoutManager(paramView);
        this.recyclerview_home.setAdapter(new HomeAdapter(new HomePage(), getActivity(), getFragmentManager()));
    }
    */
    /*
    public static HomePage getHomePageFromJson(JSONObject paramJSONObject)
    {
        HomePage localHomePage = new HomePage();
        try
        {
            localHomePage.setCode(paramJSONObject.getInt("code"));
            localHomePage.setType(paramJSONObject.getString("type"));
            localHomePage.setStatus(paramJSONObject.getString("status"));
            paramJSONObject = paramJSONObject.getJSONObject("data");
            Log.d("Head Line", paramJSONObject.getString("headline"));
            localHomePage.setHeadlines(Data.getDataFromJson(paramJSONObject.getString("headline")));
            localHomePage.setTrendingnow(Data.getDataFromJson(paramJSONObject.getString("trendingnow")));
            localHomePage.setNewrelease(Data.getDataFromJson(paramJSONObject.getString("newrelease")));
            return localHomePage;
        }
        catch (JSONException paramJSONObject)
        {
            paramJSONObject.printStackTrace();
        }
        return localHomePage;
    }
    */
    @Override
    public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle) {

        View view = paramLayoutInflater.inflate(R.layout.fragment_home, paramViewGroup, false);
        //initLayout(paramLayoutInflater);
        //initPostApi(paramLayoutInflater);
        initAction();

        return view;
    }

    private void initLayout(LinearLayout paramView) {
        this.recyclerview_home = ((RecyclerView)paramView.findViewById(R.id.recyclerview_home));
        this.swipe = ((SwipeRefreshLayout)paramView.findViewById(R.id.swipe));
        initLayoutParam(paramView);
        this.loading = ((ProgressBar)paramView.findViewById(R.id.loading));
    }

    private void initLayoutParam(LinearLayout paramView) {
        paramView = new LinearLayout(paramView.getContext());
        paramView.setOrientation(LinearLayout.VERTICAL);
        this.recyclerview_home.setLayoutManager(new LinearLayoutManager(getActivity()));
        //this.recyclerview_home.setAdapter(new HomeAdapter(new HomePage(), getActivity(), getFragmentManager()));
    }
}
