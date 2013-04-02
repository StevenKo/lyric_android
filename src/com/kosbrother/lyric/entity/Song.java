package com.kosbrother.lyric.entity;

public class Song {
    int    id;
    String name;

    public Song() {
        this(1, "");
    }

    public Song(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
