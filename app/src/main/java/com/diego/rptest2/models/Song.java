package com.diego.rptest2.models;

public class Song {
    private String title,artist,album,data;

    public Song(String title, String artist, String album, String data) {
        this.title = title;
        this.artist = artist;
        this.album = album;
        this.data = data;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public String getAlbum() {
        return album;
    }

    public String getData() {
        return data;
    }
}
