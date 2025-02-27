package model;

/*
 * File: PlayList.java
 * Author: Maya Vanderpool
 * Purpose: This class defines a PlayList object.
 */

import java.util.ArrayList;

public class PlayList {

    /* INSTANCE VARIABLES */
    private ArrayList<Song> playlist;
    private String name;

    /* CONSTRUCTOR */
    public PlayList(String name) {
        this.playlist = new ArrayList<Song>();
        this.name = name;
    }

    /* METHODS */

    public String getName() {
        return name;
    }

    public ArrayList<Song> getPlaylist() {
        ArrayList<Song> list = new ArrayList<>();
        for (Song s : playlist) {
            list.add(new Song(s));
        }
        return list;
    }

    public void addSong(Song song) {
        playlist.add(new Song(song));
    }
}