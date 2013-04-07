package com.kosbrother.lyric.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.kosbrother.lyric.entity.Album;
import com.kosbrother.lyric.entity.SingerCategory;
import com.kosbrother.lyric.entity.SingerSearchWay;
import com.kosbrother.lyric.entity.SingerSearchWayItem;

public class LyricAPI {
    final static String         HOST  = "http://106.187.102.167";
    public static final String  TAG   = "LyricAPI";
    public static final boolean DEBUG = true;

    public static Album getAlbum(int album_id) {
        String message = getMessageFromServer("GET", "/api/v1/albums/" + album_id + ".json", null);
        if (message == null) {
            return null;
        } else {

            try {
                JSONObject nObject;
                nObject = new JSONObject(message.toString());
                int id = nObject.getInt("id");
                String name = nObject.getString("name");
                String description = nObject.getString("description");
                String release = nObject.getString("release_time");
                Date release_time = null;
                if (!release.equals("null")) {
                    SimpleDateFormat createFormatter = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss'Z'");
                    release_time = createFormatter.parse(release);
                }

                return new Album(id, name, release_time, description);
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            } catch (ParseException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    public static ArrayList<Album> getCategoryHotAlbums(int category_id) {
        ArrayList<Album> albums = new ArrayList<Album>();
        String message = getMessageFromServer("GET", "/api/v1/albums/hot_albums.json?category_id=" + category_id, null);
        if (message == null) {
            return null;
        } else {
            return parseAlbums(message, albums);
        }
    }

    public static ArrayList<Album> getSingerAlbums(int singer_id) {
        ArrayList<Album> albums = new ArrayList<Album>();
        String message = getMessageFromServer("GET", "/api/v1/albums.json?singer_id=" + singer_id, null);
        if (message == null) {
            return null;
        } else {
            return parseAlbums(message, albums);
        }
    }

    public static ArrayList<Album> getNewAlbums(int page) {
        ArrayList<Album> albums = new ArrayList<Album>();
        String message = getMessageFromServer("GET", "/api/v1/albums/new_albums.json?page=" + page, null);
        if (message == null) {
            return null;
        } else {
            return parseAlbums(message, albums);
        }
    }

    public static ArrayList<SingerCategory> getSingerCategories() {
        return SingerCategory.getCategories();
    }

    public static ArrayList<SingerSearchWay> getSingerCategoryWays(int singerCategoryId) {
        return SingerSearchWay.getSingerCategoryWays(singerCategoryId);
    }

    public static ArrayList<SingerSearchWayItem> getSingerSearchWayItems(int singerSearchWayId) {
        return SingerSearchWayItem.getSingerSearchWayItems(singerSearchWayId);
    }

    private static ArrayList<Album> parseAlbums(String message, ArrayList<Album> albums) {
        try {
            JSONArray jArray;
            jArray = new JSONArray(message.toString());
            for (int i = 0; i < jArray.length(); i++) {

                int id = jArray.getJSONObject(i).getInt("id");
                String name = jArray.getJSONObject(i).getString("name");
                String release = jArray.getJSONObject(i).getString("release_time");

                if (!release.equals("null")) {
                    SimpleDateFormat createFormatter = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss'Z'");
                    Date release_time = createFormatter.parse(release);
                    Album album = new Album(id, name, release_time, null);
                    albums.add(album);
                } else {
                    Album album = new Album(id, name, null, null);
                    albums.add(album);
                }

            }

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
        return albums;
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
