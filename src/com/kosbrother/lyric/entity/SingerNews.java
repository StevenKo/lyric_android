package com.kosbrother.lyric.entity;

public class SingerNews {

    String title;
    String source;
    String pic_link;
    String link;

    public SingerNews() {
        this("", "", "", "");
    }

    public SingerNews(String title, String source, String pic_link, String link) {
        this.title = title;
        this.source = source;
        this.pic_link = pic_link;
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public String getSource() {
        return source;
    }

    public String getPicLink() {
        return pic_link;
    }

    public String getLink() {
        return link;
    }

}
