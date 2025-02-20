package model;

import java.util.ArrayList;

/*
* File: Album.java
* Author:Rees Hart
* Purpose: This class represents an album 
*/

public class Album {

	/* INSTANCE VARIABLES */
	private String title;
	private String artist;
	private int year;
	private String genre;
	private ArrayList<Song> songList;

	/* CONSTRUCTOR */
	public Album(String title, String artist, int year, String genre) {
		this.title = title;
		this.artist = artist;
		this.year = year;
		this.genre = genre;
		this.songList = new ArrayList<Song>();
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

	public ArrayList<Song> getSongList() {
		ArrayList<Song> songs = new ArrayList<Song>();
		for (Song s : songList) {
			songs.add(new Song(s));
		}
		return songs;
	}

	// addSong(Song song) -- adds a song to the songList
	public void addSong(Song song) {
		songList.add(song);
	}

}
