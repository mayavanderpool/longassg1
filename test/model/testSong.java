package test;

/*
 * File: Song.java
 * Author: Maya Vanderpool
 * Purpose: This is a test class for Song.java.
 */

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import model.Album;
import model.LibraryModel;
import model.MusicStore;
import model.Song;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class testSong {

	@Test
	void testRating() {
		LibraryModel model = new LibraryModel();
		MusicStore store = new MusicStore();
		Album al = store.getAlbumList().get(0);
		model.addAlbum(al.getTitle());
		
		model.getSongs().get(11).setRating(5);
		assertEquals(5, model.getSongs().get(11).getRating());

	}

	@Test
	void testFavorite() {
		MusicStore store = new MusicStore();
		Song song1 = store.getSongList().get(0);

		assertFalse(song1.getFavorite());

		song1.setFavorite();
		assertTrue(song1.getFavorite());

	}

	@Test
	void testGetTitle() {
		MusicStore store = new MusicStore();
		Song song1 = store.getSongList().get(0);

		assertEquals(song1.getTitle(), "Daydreamer");

	}

	@Test
	void testGetArtist() {
		MusicStore store = new MusicStore();
		Song song1 = store.getSongList().get(0);

		assertEquals(song1.getArtist(), "Adele");

	}
	
	@Test
	void testPrintSong() {
		MusicStore store = new MusicStore();
		Song song1 = store.getSongList().get(0);
		String test = "Daydreamer,Adele,19\n";
		assertTrue(song1.printSong().equals(test));
	}
	
	@Test 
	void testPlaying() {
		MusicStore store = new MusicStore();
		Song song1 = store.getSongList().get(0);
		song1.playSong();
		assertEquals(song1.getPlays(), 1);
		song1.playSong();
		song1.playSong();
		song1.playSong();
		assertEquals(song1.getPlays(), 4);
	}
	
	@Test 
	void getGenre() {
		MusicStore store = new MusicStore();
		Song song1 = store.getSongList().get(0);
		assertEquals(song1.getGenre(), "Pop");
	}
	
	

}
