package com.example.albert.popularmovies;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new DetailFragment())
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu) {
        // Inflate the menu; this adds items to the actin bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        // noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public static class DetailFragment extends Fragment {
        private static final String LOG_TAG = DetailActivity.class.getSimpleName();

        public DetailFragment() {
            setHasOptionsMenu(true);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_detail, container, false);

            MovieInfo movieData;

            // The detail Activity called via intent. Inspect the intent for data.
            Intent intent = getActivity().getIntent();
            if (intent != null) {
                movieData = intent.getParcelableExtra("movieStrings");
                ((TextView) rootView.findViewById(R.id.movie_title)).setText(movieData.movieName);

                ImageView imageView = (ImageView) rootView.findViewById(R.id.movie_poster);

                Picasso.with(getActivity())
                        .load("http://image.tmdb.org/t/p/w185" + movieData.posterURL)
                        .into(imageView);

                Resources res = getResources();
                String detailDate = res.getString(R.string.release_date, movieData.releaseDate);
                String detailRating = res.getString(R.string.vote_average, movieData.usrRating);

                ((TextView) rootView.findViewById(R.id.movie_synopsis)).setText(movieData.overview);
                ((TextView) rootView.findViewById(R.id.movie_date)).setText(detailDate);
                ((TextView) rootView.findViewById(R.id.movie_rating)).setText(detailRating);

            }

            return rootView;
        }

    }
}
