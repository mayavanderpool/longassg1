package model;

/*
 * File: Song.java
 * Author: Maya Vanderpool
 * Purpose: This is a test class for Song.java.
 */

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

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
		Song song1 = new Song("Red Wine Supernova", "Chappelle Roan");

		assertFalse(song1.getFavorite());

		song1.setFavorite(true);
		assertTrue(song1.getFavorite());

	}

	@Test
	void testGetTitle() {
		Song song1 = new Song("Red Wine Supernova", "Chappelle Roan");

		assertEquals(song1.getTitle(), "Red Wine Supernova");

	}

	@Test
	void testGetArtist() {
		Song song1 = new Song("Red Wine Supernova", "Chappelle Roan");

		assertEquals(song1.getArtist(), "Chappelle Roan");

	}

}
