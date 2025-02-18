package model;

/*
 * File: PlayList.java
 * Author: Maya Vanderpool
 * Purpose: This class defines a PlayList object.
 */

import java.util.ArrayList;

public class PlayList {

    // instance variables
    private ArrayList<Song> playlist;
    private String name;

    // constructor
    public PlayList() {
        this.playlist = new ArrayList<Song>();
        this.name = null;
    }

    // methods
    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Song> getPlaylist() {
        ArrayList<Song> list = new ArrayList<>();
        for (Song s : playlist) {
            list.add(s);
        }
        return list;
    }

    public void addSong(String title, String author) {
        playlist.add(new Song(title, author));
    }
}