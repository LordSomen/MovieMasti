package com.example.android.moviemasti.DataManipulation;

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
    //TODO check the response code
    //TODO add connection time  out
    public static String getJSONResponseFromUrl(String responseUrl)  {
        String jsonPopularMovieData = null;
        URL httpResponseUrl = makeUrl(responseUrl);
        HttpsURLConnection httpsURLConnection = null;
        if (httpResponseUrl != null) {
            try {
                httpsURLConnection = (HttpsURLConnection) httpResponseUrl.openConnection();

               // httpsURLConnection.setReadTimeout(3000);
                // Timeout for connection.connect() arbitrarily set to 3000ms.
                //httpsURLConnection.setConnectTimeout(3000);
                // For this use case, set HTTP method to GET.
                httpsURLConnection.setInstanceFollowRedirects(true);

                httpsURLConnection.setUseCaches(true);
                httpsURLConnection.setDefaultUseCaches(true);
                httpsURLConnection.setRequestMethod("GET");
                // Already true by default but setting just in case; needs to be true since this request
                // is carrying an input (response) body.
                httpsURLConnection.setDoInput(true);
                // Open communications link (network traffic occurs here).
                httpsURLConnection.connect();
                if(httpsURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    InputStream inputStream = httpsURLConnection.getInputStream();
                    Scanner scanner = new Scanner(inputStream);
                    scanner.useDelimiter("\\A");
                    if (scanner.hasNext()) {
                        jsonPopularMovieData = scanner.next();
                        return jsonPopularMovieData;
                    }
                }
                else {
                    return null;
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
