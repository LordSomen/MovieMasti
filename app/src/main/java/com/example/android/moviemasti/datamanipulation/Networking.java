package com.example.android.moviemasti.datamanipulation;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by soumyajit on 22/9/17.
 */

public class Networking {
    public static String getJSONResponseFromUrl(String responseUrl) {
        String jsonPopularMovieData = null;
        URL httpResponseUrl = makeUrl(responseUrl);
        HttpsURLConnection httpsURLConnection = null;
        if (httpResponseUrl != null) {
            try {
                httpsURLConnection = (HttpsURLConnection) httpResponseUrl.openConnection();
                httpsURLConnection.setReadTimeout(3000);
                httpsURLConnection.setConnectTimeout(3000);
                httpsURLConnection.setInstanceFollowRedirects(true);
                httpsURLConnection.setUseCaches(true);
                httpsURLConnection.setDefaultUseCaches(true);
                httpsURLConnection.setRequestMethod("GET");

                httpsURLConnection.setDoInput(true);

                httpsURLConnection.connect();
                if (httpsURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    InputStream inputStream = httpsURLConnection.getInputStream();
                    Scanner scanner = new Scanner(inputStream);
                    scanner.useDelimiter("\\A");
                    if (scanner.hasNext()) {
                        jsonPopularMovieData = scanner.next();
                        return jsonPopularMovieData;
                    }
                } else {
                    return null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (httpsURLConnection != null)
                    httpsURLConnection.disconnect();
            }
        }
        return jsonPopularMovieData;
    }

    public static URL makeUrl(String url) {
        URL createdUrl = null;
        try {
            if (url != null) {
                createdUrl = new URL(url);
                return createdUrl;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return createdUrl;
    }
}
