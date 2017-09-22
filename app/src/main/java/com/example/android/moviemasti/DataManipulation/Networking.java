package com.example.android.moviemasti.DataManipulation;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by soumyajit on 22/9/17.
 */

public class Networking {

    public static String getJSONResponseFromUrl(String responseUrl) throws IOException {
        String jsonPopularMovieData = null;
        URL httpResponseUrl = makeUrl(responseUrl);
        if (httpResponseUrl != null) {
            HttpsURLConnection httpsURLConnection = (HttpsURLConnection) httpResponseUrl.openConnection();
            try {
                InputStream inputStream = httpsURLConnection.getInputStream();
                Scanner scanner = new Scanner(inputStream);
                scanner.useDelimiter("\\A");
                if (scanner.hasNext()) {
                    jsonPopularMovieData = scanner.next();
                    return jsonPopularMovieData;
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
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
