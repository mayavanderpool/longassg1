package model;

/*
 * File: Song.java
 * Author: Maya Vanderpool and Rees Hart
 * Purpose: This class defines a song object.
 */

public class Song {

    /* INSTANCE VARIABLES */

    private String title;
    private String artist;
    private int rating;
    private boolean favorite;
    private Album album;

    /* CONSTRUCTOR */

    public Song(String title, String artist, Album album) {
        this.title = title;
        this.artist = artist;
        this.rating = 0;
        this.favorite = false;
        this.album = album;
    }

    // copy constructor
    public Song(Song song) {
        this.title = song.title;
        this.artist = song.artist;
        this.rating = song.getRating();
        this.favorite = song.getFavorite();
        this.album = song.getAlbum();
    }

    /* SETTERS/GETTERS */

    public void setRating(int rating) {
        this.rating = rating;
    }
    
    public void setFavorite() {
        this.favorite = true;
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
    
    //printSong() - returns a string representation of the song object
    public String printSong() {
    	String albumName = (this.getAlbum()).getTitle();
    	String songStr = this.getTitle() + "," + this.getArtist() + "," + albumName + "\n";
    	return songStr;
    } 

    

}