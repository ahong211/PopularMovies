package com.albert.popularmoviesredux;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class MainActivity extends AppCompatActivity {

    ImageView showExampleImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showExampleImage = (ImageView) findViewById(R.id.iv_main_activity);

        Glide.with(this)
                .load("http://www.icon2s.com/wp-content/uploads/2014/04/black-white-android-film-reel.png")
                .into(showExampleImage);
    }
}
