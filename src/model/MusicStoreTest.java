package model;


import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;



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
		
		
		store.searchAlbumByArtist(" Adele");
	}
	
} 
