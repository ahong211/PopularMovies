package com.albert.popularmoviesredux;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class MainActivity extends AppCompatActivity {

    TextView showText;
    ImageView showExampleImage;
    FetchMovieTask mFetchMovieTask = new FetchMovieTask();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initilizeLayouts();
        mFetchMovieTask.execute();

        Glide.with(this)
                .load("http://www.icon2s.com/wp-content/uploads/2014/04/black-white-android-film-reel.png")
                .into(showExampleImage);
    }

    private void initilizeLayouts() {
        showText = (TextView) findViewById(R.id.tv_show_text);
        showExampleImage = (ImageView) findViewById(R.id.iv_main_activity);
    }

    public class FetchMovieTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return "Done with sleeping threads";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            showText.setText(s);
        }
    }
}
