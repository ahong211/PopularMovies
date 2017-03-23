package com.example.albert.popularmovies;


import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFragment extends Fragment {

    private MovieAdapter mMovieAdapter;
    private ArrayList<MovieInfo> movieInfoArrayList = new ArrayList<MovieInfo>();

    public MovieFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null || !savedInstanceState.containsKey("movieDB")) {
            movieInfoArrayList = new ArrayList<MovieInfo>();
        }
        else {
            movieInfoArrayList = savedInstanceState.getParcelableArrayList("movieDB");
        }
        // Add this line in order for this fragment to handles menu events.
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    //    inflater.inflate(R.menu.menu_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml
        int id = item.getItemId();
        if (id == R.id.action_refresh) {

            updateMovieScreen();

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList("movieDB", movieInfoArrayList);
        super.onSaveInstanceState(outState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_movie, container, false);

        mMovieAdapter = new MovieAdapter(getActivity(), movieInfoArrayList);

        GridView gridView = (GridView) rootView.findViewById(R.id.poster_grid);
        gridView.setAdapter(mMovieAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MovieInfo tempHolder = (MovieInfo) mMovieAdapter.getItem(position);

                Intent intent = new Intent(getActivity(), DetailActivity.class).putExtra("movieStrings", tempHolder);
                startActivity(intent);

            }
        });


        return rootView;
    }


    @Override
    public void onStart() {
        super.onStart();

        updateMovieScreen();
    }

    private void updateMovieScreen() {
        FetchMovieTask movieTask = new FetchMovieTask();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        movieTask.execute();
    }

    public class FetchMovieTask extends AsyncTask<Object, Object, MovieInfo[]> {

        private MovieAdapter movieAdapter;
        private final String LOG_TAG = FetchMovieTask.class.getSimpleName();

        /**
         * Take the String representing the complete forecast in JSON Format and
         * pull out the data we need to construct the Strings needed for the wireframes.
         *
         * Foretunately parsing is easy: constructor takes the JSON string and converts it
         * into an Object hierarchy for us.
         */

        private MovieInfo[] getMovieDataFromJson(String movieJsonStr) throws JSONException {

            // These are the names of the JSON objects that need to be extracted.
            final String MDB_RESULTS = "results";
            final String MDB_POSTER_PATH = "poster_path";
            final String MDB_TITLE = "title";
            final String MDB_OVERVIEW = "overview";
            final String MDB_RATING = "vote_average";
            final String MDB_DATE = "release_date";

            JSONObject movieJson = new JSONObject(movieJsonStr);
            JSONArray movieArray = movieJson.getJSONArray(MDB_RESULTS);

            MovieInfo[] resultStrs = new MovieInfo[20];


            for (int i = 0; i < movieArray.length(); i++) {
                // For now, just going to output the poster path url string
                String posterPathString;
                String movieTitle;
                String movieDesc;
                String movieRating;
                String movieDate;

                // Get the JSON object representing that movie in the json
                JSONObject numMovie = movieArray.getJSONObject(i);

                posterPathString = numMovie.getString(MDB_POSTER_PATH);
                movieTitle = numMovie.getString(MDB_TITLE);
                movieDesc = numMovie.getString(MDB_OVERVIEW);
                movieRating = numMovie.getString(MDB_RATING);
                movieDate = numMovie.getString(MDB_DATE);

                resultStrs[i] = new MovieInfo(posterPathString, movieTitle, movieDesc, movieRating, movieDate);

            }

            for (MovieInfo s : resultStrs) {
                Log.v(LOG_TAG, "Poster Entries: " + s);
            }

            return resultStrs;
        }

        @Override
        protected MovieInfo[] doInBackground(Object... params) {

            // These two need to be declared outside the try/catch
            // so that they can be closed in the finally block
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            // Will contain the raw JSON response as a string.
            String movieJsonStr = null;

    //        int numMovies = 20;

            String sortPop = "popular";
            String sortTop = "top_rated";
            Uri builtUri;

            try {
                // Construct the URL for the MovieDB query
                final String MOVIE_BASE_URL = "http://api.themoviedb.org/3/movie/";
                final String APPKEY_PARAM = "api_key";


                SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
                String sortType = sharedPrefs.getString(getString(R.string.pref_sort_key), getString(R.string.pref_sort_vPop));

                if (sortType.equals(getString(R.string.pref_sort_vTop))) {
                    builtUri = Uri.parse(MOVIE_BASE_URL).buildUpon()
                            .appendPath(sortTop)
                            .appendQueryParameter(APPKEY_PARAM, BuildConfig.POPULAR_MOVIES_API_KEY)
                            .build();
                } else {
                    Log.d(LOG_TAG, "Sort type not found:" + sortType);

                    builtUri = Uri.parse(MOVIE_BASE_URL).buildUpon()
                            .appendPath(sortPop)
                            .appendQueryParameter(APPKEY_PARAM, BuildConfig.POPULAR_MOVIES_API_KEY)
                            .build();
                }


                URL url = new URL(builtUri.toString());

                Log.v(LOG_TAG, "Built URI " + builtUri.toString());

                // Create the request to MovieDB, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }

                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty. No point in parsing.
                    return null;
                }

                movieJsonStr = buffer.toString();
            } catch (IOException e) {
                Log.e(LOG_TAG, "Error ", e);
                // If the code didn't successfully get the movieDB data, there's no point in attempting
                // to parse it.
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(LOG_TAG, "Error closing stream", e);
                    }
                }
            }

            try {
                return getMovieDataFromJson(movieJsonStr);
            } catch (JSONException e) {
                Log.e(LOG_TAG, e.getMessage(), e);
                e.printStackTrace();
            }

            // This will only happen if there was an error getting or parsing the forecast.
            return null;
        }

        @Override
        protected void onPostExecute(MovieInfo[] result) {

            if (result != null) {
                mMovieAdapter.clear();
                mMovieAdapter.addAll(result);
            }
        }
    }

}
