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
	
	public String searchSongsByTitle (String title) {
		for(Song s: songList) {
			if (s.getTitle() == title) {
				return s.getTitle();
			}
			
		}
		return null;
	}
	
	// work on these 
	public String searchSongsByArtist (String artist) {
		for(Song s: songList) {
			if (s.getArtist() == artist) {
				return s.getTitle();
			}
			
		}
		return null;
	}
	
	public String searchAlbumByTitle(String title) {
		for (Album a: albumList) {
			if(a.getTitle() == title) {
				return a.getTitle();
			}
		}
		return null;
	}
	
	public String searchAlbumByArtist(String artist) {
		for (Album a: albumList) {
			if(a.getArtist() == artist) {
				return a.getTitle();
			}
		}
		return null;
	}
	
}