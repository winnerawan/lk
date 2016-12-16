package net.winnerawan.layarkaca.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import net.winnerawan.layarkaca.R;
import net.winnerawan.layarkaca.adapter.MovieAdapter;
import net.winnerawan.layarkaca.adapter.SearchAdapter;
import net.winnerawan.layarkaca.helper.ItemClickSupport;
import net.winnerawan.layarkaca.helper.MyRequest;
import net.winnerawan.layarkaca.model.Movie;
import net.winnerawan.layarkaca.response.MovieResponse;
import net.winnerawan.layarkaca.service.ApiService;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class SearchActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private static final String TAG = SearchActivity.class.getSimpleName();

    private List<Movie> movies;
    private SearchAdapter adapter;

    @Bind(R.id.recyclerview_search) RecyclerView recycleSearch;
    @Bind(R.id.txt_search) EditText txt_seacrh;
    @Bind(R.id.txt_result_search) TextView txt_result;
    @Bind(R.id.img_close) ImageView img_close;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        overridePendingTransition(R.anim.anim_pop_down, R.anim.anim_push_down);
        img_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String query = txt_seacrh.getText().toString();
                if (query.isEmpty()) {
                    SearchActivity.this.finishAction();
                } else {
                    txt_seacrh.setText("");
                }
            }
        });
        recycleSearch.setLayoutManager(new LinearLayoutManager(this));
        getAllMovies();
        adapter = new SearchAdapter(movies, R.layout.adapter_search, getApplicationContext());
        txt_seacrh.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                final List<Movie> filteredMovie = filter(movies, txt_seacrh.getText().toString());
                adapter = new SearchAdapter(filteredMovie, R.layout.adapter_search, getApplicationContext());
                adapter.animateTo(filteredMovie);
                recycleSearch.scrollToPosition(0);
                recycleSearch.setAdapter(adapter);
                txt_result.setText(filteredMovie.size()+" result for "+txt_seacrh.getText());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable editable) {
                //filter(movies, txt_seacrh.getText().toString());
            }
        });
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        final List<Movie> filteredMovie = filter(movies, newText);
        adapter = new SearchAdapter(filteredMovie, R.layout.adapter_search, getApplicationContext());
        recycleSearch.setAdapter(adapter);

        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    private List<Movie> filter(List<Movie> models, String query) {
        query = query.toLowerCase();
        final List<Movie> filteredModelList = new ArrayList<>();
        for (Movie model : models) {
            final String title = model.getTitle().toLowerCase();
            final String genre = model.getGenre().toLowerCase();
            final String director = model.getDirector().toLowerCase();
            final String actor = model.getActor().toLowerCase();
            final String country = model.getCountry().toLowerCase();
            if (title.contains(query) || genre.contains(query) || director.contains(query)
                    || actor.contains(query) || country.contains(query)) {
                filteredModelList.add(model);
            }

        }
        return filteredModelList;
    }

    private void getAllMovies() {
        MyRequest request = new MyRequest();
        ApiService api = request.RequestMovie().create(ApiService.class);
        api.getAllMovies(new Callback<MovieResponse>() {
            @Override
            public void success(MovieResponse movieResponse, Response response) {
                boolean err = movieResponse.getError();
                if (!err) {
                    movies = movieResponse.getMovies();
                    recycleSearch.setAdapter(new SearchAdapter(movies, R.layout.adapter_search, getApplicationContext()));
                }
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });

        ItemClickSupport.addTo(recycleSearch).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                final Movie mov = (Movie) adapter.getItem(position);
                Intent i = new Intent(SearchActivity.this, DetailMovieActivity.class);
                i.putExtra("id",mov.getId());
                i.putExtra("title", mov.getTitle());
                i.putExtra("image", mov.getImage());
                startActivity(i);
            }
        });
    }

    private void finishAction() {
        finish();
        overridePendingTransition(R.anim.anim_pop_up, R.anim.anim_push_up);
    }

    public void onBackPressed() {
        finishAction();
    }
}
