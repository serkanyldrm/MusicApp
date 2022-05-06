package com.example.myapplication;

import android.net.Uri;

import java.io.Serializable;

public class Song implements Serializable {

    long id;
    Uri uri;
    String name;
    int duration;
    long albumId;
    String artist;


    public Song(long id, Uri uri, String name, int duration, long albumId, String artist) {
        this.id = id;
        this.uri = uri;
        this.name = name;
        this.duration = duration;
        this.albumId = albumId;
        this.artist = artist;
    }


    public long getId() {
        return id;
    }

    public Uri getUri() {
        return uri;
    }

    public String getName() {
        return name;
    }

    public int getDuration() {
        return duration;
    }

    public long getAlbumId() {
        return albumId;
    }

    public String getArtist() {
        return artist;
    }

}
