package test;


import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import model.Album;
import model.MusicStore;



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
		store.searchSongsByTitle("Daydreamer");
		
		store.searchSongsByTitle("Dayd");
	}
	
	@Test 
	void testSearchSongsByArtist() {
		MusicStore store = new MusicStore();
		store.searchSongsByArtist("Adele");
		store.searchSongsByArtist("Rees");
	}
	
	@Test 
	void testSearchSAlbumByTitle() {
		MusicStore store = new MusicStore();
		store.searchAlbumByTitle("19");
		store.searchAlbumByTitle("Rees");
	}
	
	@Test 
	void testSearchSAlbumByArist() {
		MusicStore store = new MusicStore();
		store.searchAlbumByArtist("Adele");
		store.searchAlbumByArtist("Rees");
	}
	
	@Test
	void testGetSongList() {
		MusicStore store = new MusicStore();
		assertFalse(store.getSongList().size()==0);
	}
} 
