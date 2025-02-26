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

    public LibraryModel(){
        this.songs = new ArrayList<Song>();
        this.playLists = new ArrayList<PlayList>();
        this.albums = new ArrayList<Album>();
    }

    public void addSong(Song song){
        songs.add(new Song(song));
    }

    public void addPlaylist(PlayList playlist){
        playLists.add(playlist);
    }

	public void addAlbum(Album album) {
		MusicStore store = new MusicStore();
		for (Album a : store.getAlbumList()) {
			if (a.getTitle() == album.getTitle() && a.getArtist() == album.getArtist()) {
				albums.add(album);
			}
		}
	}

	public void searchSongsByTitle(String title) {
		boolean found = false;
		for (Song s : songs) {
			if (s.getTitle().equals(title)) {
				found = true;
				s.printSong();
			}

		}
		if (found == false) {
			System.out.println("This song does not exist in the library.");
		}
	}

	public void searchSongsByArtist(String artist) {
		boolean found = false;
		for (Song s : songs) {
			if (s.getArtist().equals(artist)) {
				found = true;
				s.printSong();
			}
		}
		if (found == false) {
			System.out.println("This song does not exist in the library.");
		}
	}

	public void searchAlbumByTitle(String title) {
		boolean found = false;
		for (Album a : albums) {
			if (a.getTitle().equals(title)) {
				found = true;
				a.printAlbum();
			}
		}
		if (found == false) {
			System.out.println("This album does not exist in the library.");
		}
	}

	public void searchAlbumByArtist(String artist) {
		boolean found = false;
		for (Album a : albums) {
			if (a.getArtist().equals(artist)) {
				found = true;
				a.printAlbum();
			}
		}
		if (found == false) {
			System.out.println("This album does not exist in the library.");
		}
	}

	public void searchPlayList(String name) {
		for (PlayList playList : playLists) {
			if (playList.getName().equals(name)) {
				for (Song s : playList.getPlaylist()) {
					System.out.println(s.getTitle() + "," + s.getArtist());
				}
			}
		}
		System.out.println("This playlist does not exist in the library.");
	}

	public void getSongs() {
		for (Song s : songs) {
			s.printSong();
		}
	}

}
