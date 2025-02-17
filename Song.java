package model;

/*
 * File: Song.java
 * Author: Maya Vanderpool
 * Purpose: This class defines a song object.
 */

public class Song {

    // instance variables
    private String title;
    private String artist;
    private int rating;
    private boolean favorite;

    // constructor
    public Song(String title, String artist) {
        this.title = title;
        this.artist = artist;
        this.rating = 0;
        this.favorite = false;
    }

    // methods
    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public int getRating() {
        return rating;
    }

    public boolean getFavorite() {
        return favorite;
    }
    
    public static Song copySong(Song song) {
		return new Song (song.title, song.artist);
	}

}