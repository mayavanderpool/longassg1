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
    
    public PlayList deepCopy() {
    	PlayList copy = new PlayList(this.getName());
    	for(Song s : this.getPlaylist()) {
    		copy.addSong(new Song(s)); 
 
    		
    	}
    	return copy;
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
        playlist.add(song);
    }

    public void removeSong(Song song) {
        if (playlist.contains(song)) {
            playlist.remove(song);
        }
        else{
            System.out.println("This song is not in the playlist.");
        }
    }
}