package model;


import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/*
* File: MusicStoreTest.java
* Author: Rees Hart and Maya Vanderpool
* Purpose: This test file tests the various methods in the MusicStore.java class
*  and acheives 90% (or greater) code coverage.  
*/

class MusicStoreTest {
	

	@Test
	void testConstructor() {
		MusicStore store = new MusicStore();
		assertEquals(store.getAlbumList().size(),15);
	}
	
	@Test
	void testAddAlbum() {
		MusicStore store = new MusicStore();
		Album album1 = new Album ("al", "bum");
		store.addAlbum(album1);
		assertEquals(store.getAlbumList().size(),16);
		
	}
	
	@Test 
	void testSearchSongsByTitle() {
		MusicStore store = new MusicStore();
		assertEquals(store.searchSongsByTitle("Daydreamer"), "Daydreamer,Adele,19\n");
		assertEquals(store.searchSongsByTitle("Dayd"), "This song does not exist in the music store.");
	}
	
	@Test 
	void testSearchSongsByArtist() {
		MusicStore store = new MusicStore();
		assertTrue(store.searchSongsByArtist("Adele").length() != 0);
		assertEquals(store.searchSongsByArtist("Rees"), "This artist does not exist in the music store.");
	}
	
	@Test 
	void testSearchSAlbumByTitle() {
		MusicStore store = new MusicStore();
		assertTrue(store.searchAlbumByTitle("19").size() != 0);
		assertEquals(store.searchAlbumByTitle("Rees").size(),0);
	}
	
	@Test 
	void testSearchSAlbumByArist() {
		MusicStore store = new MusicStore();
		assertTrue(store.searchAlbumByArtist("Adele").size() == 0);
		assertEquals(store.searchAlbumByArtist("Rees").size(),0);
	}
	
	@Test
	void testGetSongList() {
		MusicStore store = new MusicStore();
		assertFalse(store.getSongList().size()==0);
	}
} 
