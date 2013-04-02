package com.kosbrother.lyric.entity;

import java.util.Date;

public class Album {

    int    id;
    String name;
    Date   release_time;

    public Album() {
        this(1, "", new Date());
    }

    public Album(int id, String name, Date date) {
        this.id = id;
        this.name = name;
        this.release_time = date;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Date getDate() {
        return release_time;
    }

}
