package com.albert.popularmoviesredux;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.albert.popularmoviesredux.utils.MovieDBJsonUtils;
import com.albert.popularmoviesredux.utils.NetworkUtils;

import java.net.URL;

public class MainActivity extends AppCompatActivity {

    FetchMovieTask mFetchMovieTask = new FetchMovieTask();
    RecyclerView movieRecyclerView;
    MovieAdapter adapter;
    GridLayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeLayouts();

        mLayoutManager = new GridLayoutManager(this, 2);
        movieRecyclerView.setLayoutManager(mLayoutManager);
        movieRecyclerView.hasFixedSize();
        adapter = new MovieAdapter();
        movieRecyclerView.setAdapter(adapter);

        loadMovieData();
    }

    private void initializeLayouts() {
        movieRecyclerView = (RecyclerView) findViewById(R.id.movie_container);
    }

    private void loadMovieData() {
        String popularSortOrder = "popular";
        mFetchMovieTask.execute(popularSortOrder);
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
}
