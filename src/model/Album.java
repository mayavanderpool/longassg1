package model;

import java.util.ArrayList;

/*
* File: Album.java
* Author:Rees Hart and Maya Vanderpool
* Purpose: This class represents an album. It stores the albums info 
* as well as all the songs on the album
*/

public class Album {

	/* INSTANCE VARIABLES */
	private String title;
	private String artist;
	private int year;
	private String genre;
	private ArrayList<Song> songList;

	/* CONSTRUCTOR */

	public Album(String title, String artist) {
		this.title = title;
		this.artist = artist;
		this.year = 0;
		this.genre = "genre";
		this.songList = new ArrayList<Song>();
	}
	
	public Album deepCopy() {
		Album copy = new Album(this.getTitle(), this.getArtist());
		copy.setGenre(this.genre);
		copy.setYear(this.getYear());
		for (Song s : this.getSongList()) {
			copy.addSong(s);
		}
		return copy;
	}

	/* GETTERS */

	public String getTitle() {
		return this.title;
	}

	public String getArtist() {
		return this.artist;
	}

	public int getYear() {
		return this.year;
	}

	public String getGenre() {
		return this.genre;
	}

	public void setYear(int year) {
		this.year = year;
	}
	
	public void setGenre(String genre) {
		this.genre = genre;
	}
	
	public ArrayList<Song> getSongList() {
		ArrayList<Song> songs = new ArrayList<Song>();
		for (Song s : songList) {
			songs.add(new Song(s));
		}
		return songs;
	}

	// addSong(Song song) -- adds a song to the songList
	public void addSong(Song song) {
		songList.add(new Song(song));
	}

	// printAlbum() -- prints the album info as well as all songs on the album
	public String printAlbum() {
		String albumInfo = this.getTitle() + "," + this.getArtist() + "," + this.getGenre() + "," + this.getYear() + "\n";

		return albumInfo;
		
	}
	

}
