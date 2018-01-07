package com.albert.popularmoviesredux;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.albert.popularmoviesredux.utils.MovieDBJsonUtils;
import com.albert.popularmoviesredux.utils.NetworkUtils;

import java.net.URL;

public class MainActivity extends AppCompatActivity {

    FetchMovieTask mFetchMovieTask = new FetchMovieTask();
    RecyclerView movieRecyclerView;
    MovieAdapter adapter;
    LinearLayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeLayouts();

//        Glide.with(this)
//                .load("http://www.icon2s.com/wp-content/uploads/2014/04/black-white-android-film-reel.png")
//                .into(showExampleImage);


        mLayoutManager = new LinearLayoutManager(this);
        movieRecyclerView.setLayoutManager(mLayoutManager);
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
