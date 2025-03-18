package model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/*
 * File: LibraryModel.java
 * Author: Maya Vanderpool and Rees Hart
 * Purpose: This is a test class for Song.java.
 */

public class LibraryModel {

	/* INSTANCE VARIABLES */
	private ArrayList<Song> songs;
	private ArrayList<PlayList> playLists;
	private ArrayList<Album> albums;
	private ArrayList<String> artists;
	private ArrayList<Song> favorites;

	public LibraryModel() {
		this.songs = new ArrayList<Song>();
		this.playLists = new ArrayList<PlayList>();
		this.albums = new ArrayList<Album>();
		this.artists = new ArrayList<String>();
		this.favorites = new ArrayList<Song>();
	}

	//addSong(String song) - returns a boolean whether or not to add a song
	public boolean addSong(String song) {
		MusicStore store = new MusicStore();
		boolean found = false;
		for (Song s : store.getSongList()) {
			if (song.equals(s.getTitle())) {
				found = true;
				songs.add(s);
				if (!artists.contains(s.getArtist())) {
					artists.add(s.getArtist());
				}
			}
		}
		return found;
	}
	
	public boolean removeSong(String song) {
		boolean found = false;
		
		for (int i = songs.size() - 1; i >= 0; i--) {
			if(song.equals(songs.get(i).getTitle())) {
				found = true;
				songs.remove(i);
			}
		}
		return found;
	}
	
	public boolean removeAlbum(String album) {
		boolean found = false;
		
		for (int i = albums.size() - 1; i >= 0; i--) {
			if(album.equals(albums.get(i).getTitle())) {
				found = true;
				albums.remove(i);
				//this isnt working yet 
				for(Song s: albums.get(i).getSongList()) {
					removeSong(s.getTitle());
				}
				}
			}
		return found;
	}

	//addPlaylist(PlayList playlist) - adds a playlist the arraylist of playlists
	public void addPlaylist(PlayList playlist) {
		playLists.add(playlist);
	}

	//addAlbum(String album) - adds an album to the library returns a boolean
	public boolean addAlbum(String album) {
		MusicStore store = new MusicStore();
		boolean found = false;
		for (Album a : store.getAlbumList()) {
			if (album.equals(a.getTitle())) {
				found = true;
				albums.add(a.deepCopy());
				for (Song s : a.getSongList()) {
					songs.add(s);
				}
				if (!artists.contains(a.getArtist())) {
					artists.add(a.getArtist());
				}
			}
		}
		return found;
	}

	//getSong(String title) - adds a song to the arraylist of songs
	public ArrayList<Song>  getSong(String title){
		ArrayList<Song> matches = new ArrayList<Song>();
		for(Song s : songs){
			if(s.getTitle().equals(title)){
				matches.add(new Song(s));
			}
		}
		return matches;
	}

	//PlayList(String name) - returns a deep copy of the a PlayList object
	public PlayList getPlayList(String name) {
		for (PlayList list : playLists) {
			if (list.getName().equals(name)) {
				return list;

			}
		}
		System.out.println("\nThis playlist does not exist in the library.\n");
		return null;
	}

	//searchSongsByArtist(String artist) - returns a String of all the songs by given artist
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
			out +="\n This song does not exist in the library.\n";
		}
		return out;
	}

	//searchAlbumByTitle(String title) - returns a string of the albums with the given title
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
			out +=  "\nThis album does not exist in the library. \n";
		}
		return out;
	}

	
	//searchAlbumByArtist(String artist) - returns a string of the albums by the given artist
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
			out += "\nThis album does not exist in the library.\n";
		}
		return out;
	}

	
	//getSongs() - returns an arraylist of songs from the library
	public ArrayList<Song> getSongs(){
		ArrayList<Song> all = new ArrayList<Song>();
		for(Song s : songs){
			all.add(new Song(s));
		}
		return all;
	}

	//getPlaylists() - returns an arraylist of strings from the library
	public ArrayList<String> getPlaylists() {
		ArrayList<String> list = new ArrayList<>();
		for(PlayList play:playLists){
			list.add(play.getName());
			
		}
		return list;
	}

	//getFavorites() = returns an arraylist of strings of the favorited songs
	public ArrayList<Song>  getFavorites() {
		return this.favorites;
	}

	//getArtists() - returns an arraylist of strings of the artists in the library
	public ArrayList<String> getArtists() {
		ArrayList<String> list = new ArrayList<>();
		for (String artist : artists) {
			list.add(artist);
		}
		return list;
	}

	//getAlbums() - return an arraylist of albums of the albums in the library
	public  ArrayList<Album> getAlbums() {
		ArrayList<Album> list = new ArrayList<>();
		for (Album album : albums) {
			list.add(album.deepCopy());
		}
		return list;
	}
	
	public void addFavorite(Song song) {
		favorites.add(song);
	}

	
	public String searchSongByGenre(String genre) {
		boolean found = false;
		String out = "";
		for (Song s : this.songs) {
			if (s.getGenre().equals(genre)) {
				found = true;
				out += s.printSong();
			}
		}
		if (found == false) {
			out += "\nThis genre does not exist in the library.\n";
		}
		return out;
	}
	
	public void sortSongsTitle(){
		int j = songs.size();
		boolean swap = false;
		do {
			swap = false;
			for(int i = 0; i<j - 1; i++ ) {
				if (songs.get(i).getTitle().compareTo(songs.get(i+1).getTitle()) > 0) {
						Song temp = new Song(songs.get(i));
						songs.set(i, songs.get(i+1));
						songs.set(i+1, temp);
						swap = true;
				}
			}
			j--;
		}while (swap);
	}

	public void sortSongsArtist(){
		int j = songs.size();
		boolean swap = false;
		do {
			swap = false;
			for(int i = 0; i<j - 1; i++ ) {
				if (songs.get(i).getArtist().compareTo(songs.get(i+1).getArtist()) > 0) {
						Song temp = new Song(songs.get(i));
						songs.set(i, songs.get(i+1));
						songs.set(i+1, temp);
						swap = true;
				}
			}
			j--;
		}while (swap);
	}
	
	public void sortSongsRating(){
		int j = songs.size() - 1;
		boolean swap = false;
		do {
			swap = false;
			for(int i = 0; i<j - 1; i++ ) {
				if (songs.get(i).getRating() > songs.get(i+1).getRating()) {
						Song temp = new Song(songs.get(i));
						songs.set(i, songs.get(i+1));
						songs.set(i+1, temp);
						swap = true;
				}
			}
			j--;
		}while (swap);
	}
	
		

}
