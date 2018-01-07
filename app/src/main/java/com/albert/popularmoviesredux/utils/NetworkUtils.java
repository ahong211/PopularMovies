package com.albert.popularmoviesredux.utils;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;


/**
 * Created by Albert on 1/6/18.
 */

public class NetworkUtils {

    private static final String TAG = NetworkUtils.class.getSimpleName();

    private static String BASE_URL = "http://api.themoviedb.org/3/";
    private static String MEDIA_TYPE = "movie/";
    private static String SORT_ORDER = "popular";
    private static String API_KEY = "api_key";

    public static URL buildUrl(String movieQuery) {
        Uri builtUri = Uri.parse(BASE_URL).buildUpon()
        .appendPath(MEDIA_TYPE)
                .appendPath(SORT_ORDER)
                .appendQueryParameter(API_KEY, "XXXXXXXXXXXX")
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.v(TAG, "Built URI " + url);

        return url;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}
