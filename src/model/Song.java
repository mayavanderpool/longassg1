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
    private Album album;

    // constructor
    public Song(String title, String artist) {
        this.title = title;
        this.artist = artist;
        this.rating = 0;
        this.favorite = false;
        this.album = null;
    }

    // methods
    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }
    
    public void setFavorite(Album album) {
        this.album = album;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }
    
    public Album getAlbum() {
        return album;
    }

    public int getRating() {
        return rating;
    }

    public boolean getFavorite() {
        return favorite;
    }
    
    //ENCAPSULATION BAD
    public static Song copySong(Song song) {
		return new Song (song.title, song.artist);
	}

}