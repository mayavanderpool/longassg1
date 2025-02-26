package model;

import java.util.ArrayList;

/*
* File: MusicStore.java
* Author: Rees Hart
* Purpose: This class represents the music store  
*/
public class MusicStore {
	/* INSTANCE VARIABLES */
	private ArrayList<Album> albumList;
	private ArrayList<Song> songList;
	
	/* CONSTRUCTOR*/
	public MusicStore() {
		this.albumList = new ArrayList<Album>();
		this.songList = new ArrayList<Song>();
	}
		
	public void addAlbum(Album album) {
		albumList.add(album);
	}
	
	public ArrayList<Album> getAlbumList(){
		ArrayList<Album> albums = new ArrayList<Album>();
		for (Album a : albumList) {
			albums.add(new Album(a.getTitle(), a.getArtist()));
		}
		return albums;
	}
	
	public ArrayList<Song> getSongList(){
		ArrayList<Song> songs = new ArrayList<Song>();
		for (Song s : songList) {
			songs.add(new Song(s));
		}
		return songs;
	}
	
	public void addSongs(Album album) {
		ArrayList<Song> albumsSongs = album.getSongList();
		for (Song s: albumsSongs) {
			songList.add(s);
		}
	}
	
	public void searchSongsByTitle (String title) {
		boolean found = false;
		for(Song s: songList) {
			if (s.getTitle() == title) {
				found = true;
				s.printSong();
			}
			
		}
		if (found == false) {
			System.out.println("Song not found");
		}
		
	}
	
	// work on these 
	public void searchSongsByArtist (String artist) {
		boolean found = false;
		for(Song s: songList) {
			if (s.getArtist() == artist) {
				found = true;
				s.printSong();
			}
			
		}
		if (found == false) {
			System.out.println("Song not found");
		}
	}
	
	public void searchAlbumByTitle(String title) {
		boolean found = false;
		for (Album a: albumList) {
			if(a.getTitle() == title) {
				a.printAlbum();
				found = true;
			}
		}
		if (found == false) {
			System.out.println("Album not found");
		}
	}
	
	public void searchAlbumByArtist(String artist) {
		boolean found = false;
		for (Album a: albumList) {
			if(a.getArtist() == artist) {
				a.printAlbum();
				found = true;
			}
		}
		if (found == false) {
			System.out.println("Album not found");
		}
	}
	
}