package model;

/*
 * File: PlayList.java
 * Author: Maya Vanderpool and Rees Hart
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
    
    //deepCopy() - returns a deep copy of the playlist object
    public PlayList deepCopy() {
    	PlayList copy = new PlayList(this.getName());
    	for(Song s : this.getPlaylist()) {
    		copy.addSong(new Song(s)); 
 
    		
    	}
    	return copy;
    }

    //getter
    public String getName() {
        return name;
    }

    //getPlaylist() - returns an arraylist of song objects 
    public ArrayList<Song> getPlaylist() {
        ArrayList<Song> list = new ArrayList<>();
        for (Song s : playlist) {
            list.add(new Song(s));
        }
        return list;

    }

	public int getLength(){
		int length = playlist.size();
		return length;
	}

    //addSong(Song song) - adds a song to the playlist
    public void addSong(Song song) {
        playlist.add(song);
    }

    //removeSong(Song song) - removes a song from the playlist
    public void removeSong(Song song) {
        if (playlist.contains(song)) {
            playlist.remove(song);
        }
        else{
            System.out.println("This song is not in the playlist.");
        }
    }
    
}