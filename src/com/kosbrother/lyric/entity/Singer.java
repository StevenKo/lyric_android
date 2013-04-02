package com.kosbrother.lyric.entity;


public class Singer {

    int    id;
    String name;
    String description;

    public Singer() {
        this(1, "", "");
    }

    public Singer(int i, String string, String string2) {
        this.id = i;
        this.name = string;
        this.description = string2;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

}
