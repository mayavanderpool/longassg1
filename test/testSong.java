package model;

/*
 * File: Song.java
 * Author: Maya Vanderpool
 * Purpose: This is a test class for Song.java.
 */

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class testSong {

	@Test
	void testRating() {
		Song song1 = new Song("Red Wine Supernova", "Chappelle Roan");

		assertEquals(song1.getRating(), 0);

		song1.setRating(5);
		assertEquals(song1.getRating(), 5);

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

	@Test
	void testCopySong() {
		Song song1 = new Song("Red Wine Supernova", "Chappelle Roan");

		Song songCopy = song1.copySong(song1);

		assertEquals(songCopy.getArtist(), "Chappelle Roan");

	}

}
