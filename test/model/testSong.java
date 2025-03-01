package test;

/*
 * File: Song.java
 * Author: Maya Vanderpool
 * Purpose: This is a test class for Song.java.
 */

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import model.MusicStore;
import model.Song;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class testSong {

	@Test
	void testRating() {
		MusicStore store = new MusicStore();
		Song song1 = store.getSongList().get(0);

		assertEquals(song1.getRating(), 0);

		song1.setRating(5);
		assertEquals(song1.getRating(), 5);

		Song song2 = new Song(song1);
		assertEquals(song2.getRating(), 5);

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

}
