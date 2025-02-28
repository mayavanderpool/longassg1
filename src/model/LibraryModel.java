package model;

import java.util.ArrayList;

/*
 * File: LibraryModel.java
 * Author: Maya Vanderpool
 * Purpose: This is a test class for Song.java.
 */

public class LibraryModel {

	/* INSTANCE VARIABLES */
	private ArrayList<Song> songs;
	private ArrayList<PlayList> playLists;
	private ArrayList<Album> albums;
	private ArrayList<String> artists;

	public LibraryModel() {
		this.songs = new ArrayList<Song>();
		this.playLists = new ArrayList<PlayList>();
		this.albums = new ArrayList<Album>();
		this.artists = new ArrayList<String>();
	}

	public void addSong(String song) {
		MusicStore store = new MusicStore();
		for (Song s : store.getSongList()) {
			if (song.equals(s.getTitle())) {
				songs.add(s);
				if (!artists.contains(s.getArtist())) {
					artists.add(s.getArtist());
				}
			}
		}
	}

	public void addPlaylist(PlayList playlist) {
		playLists.add(playlist);
	}

	public void addAlbum(String album) {
		MusicStore store = new MusicStore();
		for (Album a : store.getAlbumList()) {
			if (album.equals(a.getTitle())) {
				albums.add(a);
				for (Song s : a.getSongList()) {
					songs.add(s);
				}
			}
		}
	}

	public ArrayList<Song>  getSong(String title){
		ArrayList<Song> matches = new ArrayList<Song>();
		for(Song s : songs){
			if(s.getTitle().equals(title)){
				matches.add(new Song(s));
			}
		}
		return matches;
	}

	public PlayList getPlayList(String name) {
		for (PlayList list : playLists) {
			if (list.getName().equals(name)) {
				return list.deepCopy();
			}
		}
		System.out.println("This playlist does not exist in the library.");
		return null;
	}

	public String searchSongsByArtist(String artist) {
		boolean found = false;
		String out = "";
		for (Song s : songs) {
			if (s.getArtist().equals(artist)) {
				found = true;
				out += s.printSong();
			}
		}
		if (found == false) {
			out +="This song does not exist in the library.";
		}
		return out;
	}

	public String searchAlbumByTitle(String title) {
		String out = "";
		boolean found = false;
		for (Album a : albums) {
			if (a.getTitle().equals(title)) {
				found = true;
				out += a.printAlbum();
			}
		}
		if (found == false) {
			out += "This album does not exist in the library.";
		}
		return out;
	}

	

	public String searchAlbumByArtist(String artist) {
		boolean found = false;
		String out = "";
		for (Album a : albums) {
			if (a.getArtist().equals(artist)) {
				found = true;
				out += a.printAlbum();
			}
		}
		if (found == false) {
			out += "This album does not exist in the library.";
		}
		return out;
	}

	
	
	public ArrayList<Song> getSongs(){
		ArrayList<Song> all = new ArrayList<Song>();
		for(Song s : songs){
			all.add(new Song(s));
		}
		return all;
	}

	public ArrayList<String> getPlaylists() {
		ArrayList<String> list = new ArrayList<>();
		for(PlayList play:playLists){
			list.add(play.getName());
			
		}
		return list;
	}


	public ArrayList<String>  getFavorites() {
		ArrayList<String> list = new ArrayList<String>();
		for (Song s : songs) {
			if (s.getFavorite()) {
				list.add(s.printSong());
			}
		}
		return list;
	}

	public ArrayList<String> getArtists() {
		ArrayList<String> list = new ArrayList<>();
		for (String artist : artists) {
			list.add(artist);
		}
		return list;
	}


	public  ArrayList<String> getAlbums() {
		ArrayList<String> list = new ArrayList<>();
		for (Album album : albums) {
			list.add(album.getTitle());
		}
		return list;
	}

	

}
