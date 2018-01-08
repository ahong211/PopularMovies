package com.albert.popularmoviesredux;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.albert.popularmoviesredux.utils.MovieDBJsonUtils;
import com.albert.popularmoviesredux.utils.NetworkUtils;

import java.net.URL;

public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieOnClickListener {

    RecyclerView movieRecyclerView;
    MovieAdapter adapter;
    GridLayoutManager mLayoutManager;
    MovieInfo[] mInfo = MovieAdapter.mMovieInfos;
    String sortOrder = "popular";
    final private String POPULAR_SORT_ORDER = "popular";
    final private String TOP_RATED_SORT_ORDER = "top_rated";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeLayouts();

        if (savedInstanceState == null || !savedInstanceState.containsKey("movieDB")) {
            mInfo = new MovieInfo[]{};
        } else {
            mInfo = (MovieInfo[]) savedInstanceState.getParcelableArray("movieDB");
        }

        mLayoutManager = new GridLayoutManager(this, 2);
        movieRecyclerView.setLayoutManager(mLayoutManager);
        movieRecyclerView.hasFixedSize();
        adapter = new MovieAdapter(this, mInfo);
        movieRecyclerView.setAdapter(adapter);

        loadMovieData(sortOrder);
    }

    private void initializeLayouts() {
        movieRecyclerView = (RecyclerView) findViewById(R.id.movie_container);
    }

    private void loadMovieData(String sortOrder) {
        if (sortOrder == "popular") {
            new FetchMovieTask().execute(POPULAR_SORT_ORDER);
        }
        if (sortOrder == "top_rated") {
            new FetchMovieTask().execute(TOP_RATED_SORT_ORDER);
        }
    }

    @Override
    public void onClick(MovieInfo movieInfo) {
        Context context = this;
        Class detailActivityClass = DetailActivity.class;
        Intent intentToStartDetailActivity = new Intent(context, detailActivityClass);
        intentToStartDetailActivity.putExtra("movieData", movieInfo);
        startActivity(intentToStartDetailActivity);
    }

    public class FetchMovieTask extends AsyncTask<String, Void, MovieInfo[]> {

        @Override
        protected MovieInfo[] doInBackground(String... params) {

            String sortOrder = params[0];
            URL movieRequest = NetworkUtils.buildUrl(sortOrder);

            try {
                String movieJsonResponse = NetworkUtils.getResponseFromHttpUrl(movieRequest);

                MovieInfo[] movieJsonStrings = MovieDBJsonUtils.getMovieDBStringsFromJson(movieJsonResponse);

                return movieJsonStrings;

            } catch (Exception e) {
                e.printStackTrace();

                return null;
            }
        }

        @Override
        protected void onPostExecute(MovieInfo[] movieData) {
            super.onPostExecute(movieData);
            adapter.setMovieData(movieData);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArray("movieDB", mInfo);
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sort_order, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_sort_order_popular) {
            loadMovieData(POPULAR_SORT_ORDER);
            return true;
        }

        if (id == R.id.menu_sort_order_top_rated) {
            loadMovieData(TOP_RATED_SORT_ORDER);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
