package com.example.albert.popularmovies;


import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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
import java.util.Arrays;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFragment extends Fragment {

    private ArrayAdapter<String> mMovieAdapter;

    public MovieFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Add this line in order for this fragment to handles menu events.
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml
        int id = item.getItemId();
        if (id == R.id.action_refresh) {

            FetchMovieTask movieTask = new FetchMovieTask();
            movieTask.execute();

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Creating dummy strings as a test
        String[] data = {
                "example1",
                "example2",
                "example3"
        };

        List<String> exampleTest = new ArrayList<>(Arrays.asList(data));

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_movie, container, false);

        mMovieAdapter = new ArrayAdapter<String>(getActivity(),
                R.layout.list_posters,
                R.id.list_poster_textview,
                exampleTest);

        GridView gridView = (GridView) rootView.findViewById(R.id.poster_grid);
        gridView.setAdapter(mMovieAdapter);

//        ImageView imageView = (ImageView) rootView.findViewById(imageView);
//
//        Picasso.with(getActivity())
//                .load("http://www.golden-retriever-dog.com/wp-content/uploads/2015/08/golden-retriever-dog-03.jpg")
//                .into(imageView);



        return rootView;
    }

    public class FetchMovieTask extends AsyncTask<String, Void, String[]> {

        private final String LOG_TAG = FetchMovieTask.class.getSimpleName();

        /**
         * Take the String representing the complete forecast in JSON Format and
         * pull out the data we need to construct the Strings needed for the wireframes.
         *
         * Foretunately parsing is easy: constructor takes the JSON string and converts it
         * into an Object hierarchy for us.
         */

        private String[] getMovieDataFromJson(String movieJsonStr, int numMovies) throws JSONException {

            // These are the names of the JSON objects that need to be extracted.
            final String MDB_RESULTS = "results";
            final String MDB_POSTER_PATH = "poster_path";

            JSONObject movieJson = new JSONObject(movieJsonStr);
            JSONArray movieArray = movieJson.getJSONArray(MDB_RESULTS);

            String[] resultStrs = new String[numMovies];
            for (int i = 0; i < movieArray.length(); i++) {
                // For now, just going to output the poster path url string
                String posterPathString;

                // Get the JSON object representing that movie in the json
                JSONObject numMovie = movieArray.getJSONObject(i);

                posterPathString = numMovie.getString(MDB_POSTER_PATH);

                resultStrs[i] = posterPathString;
            }

            for (String s : resultStrs) {
                Log.v(LOG_TAG, "Poster Entries: " + s);
            }

            return resultStrs;
        }

        @Override
        protected String[] doInBackground(String... params) {

            // These two need to be declared outside the try/catch
            // so that they can be closed in the finally block
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            // Will contain the raw JSON response as a string.
            String movieJsonStr = null;

            int numMovies = 20;

            String sort = "popularity.desc";

            try {
                // Construct the URL for the MovieDB query
                final String MOVIE_BASE_URL = "http://api.themoviedb.org/3/discover/movie?";
                final String SORT_PARAM = "sort_by";
                final String APPKEY_PARAM = "api_key";

                Uri builtUri = Uri.parse(MOVIE_BASE_URL).buildUpon()
                        .appendQueryParameter(SORT_PARAM, sort)
                        .appendQueryParameter(APPKEY_PARAM, BuildConfig.POPULAR_MOVIES_API_KEY)
                        .build();

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
                // If the code didn't successfully get the weather data, there's no point in attempting
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
                return getMovieDataFromJson(movieJsonStr, numMovies);
            } catch (JSONException e) {
                Log.e(LOG_TAG, e.getMessage(), e);
                e.printStackTrace();
            }

            // This will only happen if there was an error getting or parsing the forecast.
            return null;
        }

        @Override
        protected void onPostExecute(String[] result) {
            if (result != null) {
                mMovieAdapter.clear();
                for (String movieStr : result) {
                    mMovieAdapter.add(movieStr);
                }
                // New data is back from the server. Hooray?
            }
        }
    }

}
