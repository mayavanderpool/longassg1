package model;

/*
* File: testLibraryModel.java
* Author: Rees Hart and Maya Vanderpool
* Purpose: This test file tests the various methods in the LibraryModel.java class
*  and acheives 90% (or greater) code coverage.  
*/

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class testLibraryModel {
	
	@Test
	void testAddSong() {
		MusicStore store = new MusicStore();
		LibraryModel model = new LibraryModel();
		
		Song song1 = store.getSongList().get(0);
		Song song2 = store.getSongList().get(1);
		
		model.addSong(song1.getTitle());
		assertEquals(1, model.getSongs().size());
		model.addSong(song2.getTitle());
		
		assertEquals(2, model.getSongs().size());
		assertEquals(1, model.getArtists().size());
		
		Album bad = new Album("bad", "bad");
		Song wrong = new Song("bad", "bad", bad);
		model.addSong("bad");
		assertEquals(2, model.getSongs().size());
		
		String name = song1.getTitle();
	
		assertEquals(model.getSong(name).get(0).getTitle(), song1.getTitle());
		
	}
	
	@Test
	void testLibPlaylist() {
		LibraryModel model = new LibraryModel();
		PlayList list = new PlayList("great");
		
		model.addPlaylist(list);
		assertEquals(1, model.getPlaylists().size());
	}
	
	@Test
	void testAddAlbum() {
		MusicStore store = new MusicStore();
		LibraryModel model = new LibraryModel();
	
		Album ab = store.getAlbumList().get(0);
		assertEquals(0, model.getSongs().size());
		model.addAlbum(ab.getTitle());
		assertEquals(1, model.getAlbums().size());
		
	}	
	
	@Test
	void testSearchSongsByArtist() {
		MusicStore store = new MusicStore();
		LibraryModel model = new LibraryModel();
		
		Song song1 = store.getSongList().get(0);
		
		model.addSong(song1.getTitle());
	
		String out = "";
		for(Song s: model.getSongs()) {
			if(s.getArtist().equals(song1.getArtist())) {
				out += s.printSong();
			}
		}
		assertEquals(out,(model.searchSongsByArtist(song1.getArtist())));
		
		String other = "\n This song does not exist in the library.\n";
		
		assertEquals(other,(model.searchSongsByArtist("WRONG")));
	}
	
	@Test
	void testGetPlayList() {
		MusicStore store = new MusicStore();
		LibraryModel model = new LibraryModel();
		
		PlayList list = new PlayList("my playlist");
		Song song = store.getSongList().get(0);
		model.addSong(song.getTitle());
		list.addSong(song);
		model.addPlaylist(list);
		
		assertEquals(model.getPlayList("my playlist").getName(), list.getName());
		
		
	}
	
	@Test
	void testSearchAlbumByTitle() {
		MusicStore store = new MusicStore();
		LibraryModel model = new LibraryModel();
		
		Album al = store.getAlbumList().get(0);
		
		model.addAlbum(al.getTitle());
	
		String albums = model.searchAlbumByTitle(al.getTitle());
		String out = "";
		for(Album a : model.getAlbums()) {
			if(al.getTitle().equals(a.getTitle())) {
				out += a.printAlbum();
			}
		}
		assertTrue(out.equals(albums));
	}
	
	@Test
	void testSearchAlbumByArtist() {
		MusicStore store = new MusicStore();
		LibraryModel model = new LibraryModel();
		
		Album al = store.getAlbumList().get(0);
		
		model.addAlbum(al.getTitle());
	
		String albums = model.searchAlbumByArtist(al.getArtist());
		String out = "";
		for(Album a : model.getAlbums()) {
			if(al.getArtist().equals(a.getArtist())) {
				out += a.printAlbum();
			}
		}
		assertTrue(out.equals(albums));
	}
	

	@Test
	void testGetFavorites() {
		LibraryModel model = new LibraryModel();
	
		assertEquals(0, model.getFavorites().size());
		
		
	}
	
	@Test 
	void testGenre() {
		LibraryModel model = new LibraryModel();
		MusicStore store = new MusicStore();
		Album al = store.getAlbumList().get(0);
		
		model.addAlbum(al.getTitle());
		
		
		String songs = model.searchSongByGenre("Pop");
		
	}
	
	@Test 
	void removeSong() {
		LibraryModel model = new LibraryModel();
		MusicStore store = new MusicStore();
		Album al = store.getAlbumList().get(0);
		model.addAlbum(al.getTitle());
		
		assertEquals(model.getSongs().size(), 12);
		model.removeSong("Tired");
		assertEquals(model.getSongs().size(), 11);
		model.removeSong("Daydreamer");
		assertEquals(model.getSongs().size(), 10);
	}
	
	@Test 
	void removeAlbum() {
		LibraryModel model = new LibraryModel();
		MusicStore store = new MusicStore();
		Album al = store.getAlbumList().get(0);
		model.addAlbum(al.getTitle());
		
		assertEquals(model.getAlbums().size(), 1);
		model.removeAlbum("19");
		assertEquals(model.getAlbums().size(), 0);
	}
	
}