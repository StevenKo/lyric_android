package com.kosbrother.lyric.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.json.JSONObject;

import android.util.Log;

import com.kosbrother.lyric.entity.SingerCategory;
import com.kosbrother.lyric.entity.SingerSearchWay;
import com.kosbrother.lyric.entity.SingerSearchWayItem;

public class LyricAPI {
    final static String         HOST  = "http://106.187.102.167";
    public static final String  TAG   = "LyricAPI";
    public static final boolean DEBUG = true;

    public static ArrayList<SingerCategory> getSingerCategories() {
        return SingerCategory.getCategories();
    }

    public static ArrayList<SingerSearchWay> getSingerCategoryWays(int singerCategoryId) {
        return SingerSearchWay.getSingerCategoryWays(singerCategoryId);
    }

    public static ArrayList<SingerSearchWayItem> getSingerSearchWayItems(int singerSearchWayId) {
        return SingerSearchWayItem.getSingerSearchWayItems(singerSearchWayId);
    }

    public static String getMessageFromServer(String requestMethod, String apiPath, JSONObject json) {
        URL url;
        try {
            url = new URL(HOST + apiPath);
            if (DEBUG)
                Log.d(TAG, "URL: " + url);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(requestMethod);

            connection.setRequestProperty("Content-Type", "application/json;charset=utf-8");
            if (requestMethod.equalsIgnoreCase("POST"))
                connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.connect();

            if (requestMethod.equalsIgnoreCase("POST")) {
                OutputStream outputStream;

                outputStream = connection.getOutputStream();
                if (DEBUG)
                    Log.d("post message", json.toString());

                outputStream.write(json.toString().getBytes());
                outputStream.flush();
                outputStream.close();
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder lines = new StringBuilder();
            ;
            String tempStr;

            while ((tempStr = reader.readLine()) != null) {
                lines = lines.append(tempStr);
            }
            if (DEBUG)
                Log.d("MOVIE_API", lines.toString());

            reader.close();
            connection.disconnect();

            return lines.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
