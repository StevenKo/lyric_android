package com.kosbrother.lyric.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.kosbrother.lyric.entity.Album;
import com.kosbrother.lyric.entity.Singer;
import com.kosbrother.lyric.entity.SingerCategory;
import com.kosbrother.lyric.entity.SingerNews;
import com.kosbrother.lyric.entity.SingerSearchWay;
import com.kosbrother.lyric.entity.SingerSearchWayItem;
import com.kosbrother.lyric.entity.Song;
import com.kosbrother.lyric.entity.Video;
import com.kosbrother.lyric.entity.YoutubeVideo;

public class LyricAPI {
    final static String         HOST  = "http://106.187.102.167";
    public static final String  TAG   = "LyricAPI";
    public static final boolean DEBUG = true;

    public static ArrayList<Video> getHotVideos() {
        String message = getMessageFromServer("GET", "/api/v1/videos.json", null, null);
        ArrayList<Video> videos = new ArrayList<Video>();
        if (message == null) {
            return null;
        } else {
            return parseVideos(message, videos);
        }
    }

    public static ArrayList<YoutubeVideo> getYoutubeVideos(String query, int page) {
        ArrayList<YoutubeVideo> videos = new ArrayList<YoutubeVideo>();
        try {
            query = URLEncoder.encode(query, "utf-8");
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
            return null;
        }

        String url = "http://gdata.youtube.com/feeds/api/videos?q=" + query + "&start-index=" + (page * 10 + 1) + "&max-results=10&v=2&alt=json";
        String message = getMessageFromServer("GET", null, null, url);

        if (message == null) {
            return null;
        } else {
            try {
                JSONObject object = new JSONObject(message);
                JSONObject feedObject = object.getJSONObject("feed");
                JSONArray videoArray = feedObject.getJSONArray("entry");
                for (int i = 0; i < videoArray.length(); i++) {
                    String title = videoArray.getJSONObject(i).getJSONObject("title").getString("$t");
                    String link = videoArray.getJSONObject(i).getJSONArray("link").getJSONObject(0).getString("href");
                    String thumbnail = videoArray.getJSONObject(i).getJSONObject("media$group").getJSONArray("media$thumbnail").getJSONObject(0)
                            .getString("url");
                    int duration = videoArray.getJSONObject(i).getJSONObject("media$group").getJSONObject("yt$duration").getInt("seconds");
                    int viewCount = videoArray.getJSONObject(i).getJSONObject("yt$statistics").getInt("viewCount");
                    // int likes = videoArray.getJSONObject(i).getJSONObject("yt$rating").getInt("numLikes");
                    // int dislikes = videoArray.getJSONObject(i).getJSONObject("yt$rating").getInt("numDislikes");
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
                    Date uploadTime = null;
                    try {
                        uploadTime = sdf.parse(videoArray.getJSONObject(i).getJSONObject("published").getString("$t"));
                    } catch (ParseException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    YoutubeVideo video = new YoutubeVideo(title, link, thumbnail, uploadTime, viewCount, duration);
                    videos.add(video);
                }

            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        }
        return videos;

    }

    // perpage has 8 results
    // page start from 0
    public static ArrayList<SingerNews> getNews(String query, int page) {
        ArrayList<SingerNews> newses = new ArrayList<SingerNews>();

        try {
            query = URLEncoder.encode(query, "utf-8");
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
            return null;
        }

        String url = "https://ajax.googleapis.com/ajax/services/search/news?v=1.0&q=" + query + "&start=" + page * 8 + "&rsz=8";
        String message = getMessageFromServer("GET", null, null, url);

        if (message == null) {
            return null;
        } else {
            try {
                JSONObject object = new JSONObject(message);
                JSONObject feedObject = object.getJSONObject("responseData");
                JSONArray newsArray = feedObject.getJSONArray("results");
                for (int i = 0; i < newsArray.length(); i++) {

                    String title = newsArray.getJSONObject(i).getString("title");
                    String source = newsArray.getJSONObject(i).getString("publisher");
                    String pic_link = newsArray.getJSONObject(i).getJSONObject("image").getString("tbUrl");
                    String link = newsArray.getJSONObject(i).getString("unescapedUrl");
                    SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy kk:mm:ss Z", Locale.ENGLISH);
                    Date releaseTime = null;
                    try {
                        String parseString = newsArray.getJSONObject(i).getString("publishedDate");
                        releaseTime = format.parse(parseString);
                    } catch (ParseException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                    SingerNews news = new SingerNews(title, source, pic_link, link, releaseTime);
                    newses.add(news);
                }
                return newses;

            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        }

    }

    public static Song getSong(int song_id) {
        String message = getMessageFromServer("GET", "/api/v1/songs/" + song_id + ".json", null, null);
        if (message == null) {
            return null;
        } else {
            try {
                JSONObject nObject;
                nObject = new JSONObject(message.toString());
                int id = nObject.getInt("id");
                String name = nObject.getString("name");
                String lyric = nObject.getString("lyric");
                int album_id = 0;

                if (!nObject.isNull("album_id"))
                    album_id = nObject.getInt("album_id");

                return new Song(id, name, lyric, album_id);
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    public static ArrayList<Song> getSingerSongs(int singer_id, int page) {
        String message = getMessageFromServer("GET", "/api/v1/songs.json?singer_id=" + singer_id + "&page=" + page, null, null);
        ArrayList<Song> songs = new ArrayList<Song>();
        if (message == null) {
            return null;
        } else {
            return parseSongs(message, songs);
        }
    }

    public static ArrayList<Song> getCategoryHotSongs(int singer_category_id, int page) {
        String message = getMessageFromServer("GET", "/api/v1/songs/hot_songs.json?category_id=" + singer_category_id + "&page=" + page, null, null);
        ArrayList<Song> songs = new ArrayList<Song>();
        if (message == null) {
            return null;
        } else {
            return parseSongs(message, songs);
        }
    }

    public static Singer getSinger(int singer_id) {
        String message = getMessageFromServer("GET", "/api/v1/singers/" + singer_id + ".json", null, null);
        if (message == null) {
            return null;
        } else {
            try {
                JSONObject nObject;
                nObject = new JSONObject(message.toString());
                int id = nObject.getInt("id");
                String name = nObject.getString("name");
                String description = nObject.getString("description");

                return new Singer(id, name, description);
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        }

    }

    public static ArrayList<Singer> getCategoryHotSingers(int singer_category_id, int page) {
        String message = getMessageFromServer("GET", "/api/v1/singers/hot_singers.json?singer_category_id=" + singer_category_id + "&page=" + page, null, null);
        ArrayList<Singer> singers = new ArrayList<Singer>();
        if (message == null) {
            return null;
        } else {
            return parseSingers(message, singers);
        }
    }

    public static ArrayList<Singer> getSingers(int singerSearchWayItemId, int page) {
        String message = getMessageFromServer("GET", "/api/v1/singers.json?serch_item_id=" + singerSearchWayItemId + "&page=" + page, null, null);
        ArrayList<Singer> singers = new ArrayList<Singer>();
        if (message == null) {
            return null;
        } else {
            return parseSingers(message, singers);
        }
    }

    public static Album getAlbum(int album_id) {
        String message = getMessageFromServer("GET", "/api/v1/albums/" + album_id + ".json", null, null);
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
        String message = getMessageFromServer("GET", "/api/v1/albums/hot_albums.json?category_id=" + category_id, null, null);
        if (message == null) {
            return null;
        } else {
            return parseAlbums(message, albums);
        }
    }

    public static ArrayList<Album> getSingerAlbums(int singer_id) {
        ArrayList<Album> albums = new ArrayList<Album>();
        String message = getMessageFromServer("GET", "/api/v1/albums.json?singer_id=" + singer_id, null, null);
        if (message == null) {
            return null;
        } else {
            return parseAlbums(message, albums);
        }
    }

    public static ArrayList<Album> getNewAlbums(int page) {
        ArrayList<Album> albums = new ArrayList<Album>();
        String message = getMessageFromServer("GET", "/api/v1/albums/new_albums.json?page=" + page, null, null);
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

    private static ArrayList<Video> parseVideos(String message, ArrayList<Video> videos) {
        try {
            JSONArray jArray;
            jArray = new JSONArray(message.toString());
            for (int i = 0; i < jArray.length(); i++) {

                String title = jArray.getJSONObject(i).getString("title");
                String youtube_id = jArray.getJSONObject(i).getString("youtube_id");
                Video video = new Video(title, youtube_id);
                videos.add(video);
            }

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return videos;
    }

    private static ArrayList<Song> parseSongs(String message, ArrayList<Song> songs) {
        try {
            JSONArray jArray;
            jArray = new JSONArray(message.toString());
            for (int i = 0; i < jArray.length(); i++) {

                int id = jArray.getJSONObject(i).getInt("id");
                String name = jArray.getJSONObject(i).getString("name");
                int album_id = 0;

                if (!jArray.getJSONObject(i).isNull("album_id"))
                    album_id = jArray.getJSONObject(i).getInt("album_id");

                songs.add(new Song(id, name, "null", album_id));

            }

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return songs;
    }

    private static ArrayList<Singer> parseSingers(String message, ArrayList<Singer> singers) {
        try {
            JSONArray jArray;
            jArray = new JSONArray(message.toString());
            for (int i = 0; i < jArray.length(); i++) {

                int id = jArray.getJSONObject(i).getInt("id");
                String name = jArray.getJSONObject(i).getString("name");
                singers.add(new Singer(id, name, "null"));

            }

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return singers;
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
                    Album album = new Album(id, name, release_time, "null");
                    albums.add(album);
                } else {
                    Album album = new Album(id, name, null, "null");
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

    public static String getMessageFromServer(String requestMethod, String apiPath, JSONObject json, String apiUrl) {
        URL url;
        try {
            if (apiUrl != null)
                url = new URL(apiUrl);
            else
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
