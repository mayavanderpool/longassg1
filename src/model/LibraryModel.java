package model;

import java.util.ArrayList;

/*
 * File: LibraryModel.java
 * Author: Maya Vanderpool
 * Purpose: This is a test class for Song.java.
 */

public class LibraryModel {

    /* INSTANCE VARIABLES */
    private ArrayList<Song> songs;
    private ArrayList<PlayList> playLists;
    private ArrayList<Album> albums;

    public LibraryModel(){
        this.songs = new ArrayList<Song>();
        this.playLists = new ArrayList<PlayList>();
        this.albums = new ArrayList<Album>();
    }

    public void addSong(Song song){
        songs.add(new Song(song));
    }

    public void addPlaylist(PlayList playlist){
        playLists.add(playlist);
    }

    public void addAlbum(Album album){
        albums.add(album);
    }

    
}
