package model;


import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;



class MusicStoreTest {
	

	@Test
	void testConstructor() {
		MusicStore store = new MusicStore();
		assertTrue(store.getAlbumList().size() == 0);
		assertTrue(store.getSongList().size() == 0);
	}
	
	@Test
	void testAddAlbum_AndAddSong() {
		MusicStore store = new MusicStore();
		Album album1 = new Album("Rees", "Hart");
		Song mySong = new Song("on and on", "rees");
		album1.addSong(mySong);
		album1.addSong(mySong);
		album1.addSong(mySong);
		
		store.addAlbum(album1);
		assertTrue(store.getAlbumList().size() == 1);
		
		store.addSongs(album1);
		
		assertTrue(store.getSongList().size() == 3);
		
	}
	
	@Test
	void testAddSearchForSongByTitle() {
		MusicStore store = new MusicStore();
		Album album1 = new Album("Rees", "Hart");
		Song mySong = new Song("on and on", "rees");
		Song mySong1 = new Song("on and off", "rees");
		album1.addSong(mySong);
		album1.addSong(mySong1);
		assertEquals( null, store.searchSongsByTitle("on and on"));
		
		store.addSongs(album1);
		assertEquals( mySong.getTitle(), store.searchSongsByTitle("on and on"));
		assertEquals( mySong1.getTitle(), store.searchSongsByTitle("on and off"));
	}
	
	@Test
	void testAddSearchForSongByArtist() {
		MusicStore store = new MusicStore();
		Album album1 = new Album("Rees", "Hart");
		Song mySong = new Song("on and on", "rees");
		Song mySong1 = new Song("on and off", "grees");
		album1.addSong(mySong);
		album1.addSong(mySong1);
		assertEquals( null, store.searchSongsByArtist("rees"));
		
		store.addSongs(album1);
		assertEquals( mySong.getTitle(), store.searchSongsByArtist("rees"));
		assertEquals( mySong1.getTitle(), store.searchSongsByArtist("grees"));
	}
	
	
	@Test
	void testAddSearchForAlbumByTitle() {
		MusicStore store = new MusicStore();
		Album album1 = new Album("Rees", "Hart");
		store.addAlbum(album1);
		
		assertEquals( null, store.searchAlbumByTitle("rees"));
		assertEquals( "Rees", store.searchAlbumByTitle("Rees"));
		
		
	}
	
	@Test
	void testAddSearchForAlbumByArtist() {
		MusicStore store = new MusicStore();
		Album album1 = new Album("Rees", "Hart");
		store.addAlbum(album1);
		
		assertEquals( null, store.searchAlbumByArtist("rees"));
		assertEquals( "Rees", store.searchAlbumByArtist("Hart"));
		
		
	}
	

}
