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
	
	//BAD
	public ArrayList<Album> getAlbumList(){
		return albumList;
	}
	
	//BAD 
	public ArrayList<Song> getSongList(){
		return songList;
	}
	
	public void addSongs(Album album) {
		ArrayList<Song> albumsSongs = album.getSongList();
		for (Song s: albumsSongs) {
			songList.add(s);
		}
	}
	
	public void searchSongsByTitle (String title) {
		for(Song s: songList) {
			if (s.getTitle() == title) {
				String artist = s.getArtist();
				String album = s.getAlbum().getTitle();
				System.out.println(title + "," + artist + "," + album);
			}
		}
	}
	
	// work on these 
	public Song searchSongsByArtist (String artist) {
		for(Song s: songList) {
			if (s.getArtist() == artist) {
				return s;
			}
			
		}
		return null;
	}
	
	public void searchAlbumByTitle(String title) {
		for (Album a: albumList) {
			if(a.getTitle() == title) {
				System.out.println(title + "by" + a.getArtist());
			}
		}
	}
	
	public void searchAlbumByArtist(String artist) {
		for (Album a: albumList) {
			if(a.getArtist() == artist) {
				System.out.println(a.getTitle() + "by" + artist);
			}
		}
	}
	
}