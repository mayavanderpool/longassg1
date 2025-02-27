package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class AlbumTest {

	@Test
	void testGetters() {
		
		Album spinsAlbum = new Album ("Chicken Wings and Fries", "Crispy");
		spinsAlbum.setYear(1996);
		spinsAlbum.setGenre("Expeirmental Hip-Hop");
		
		assertEquals("Crispy", spinsAlbum.getArtist());
		assertEquals("Chicken Wings and Fries", spinsAlbum.getTitle());
		assertEquals(1996, spinsAlbum.getYear());
		assertEquals("Expeirmental Hip-Hop", spinsAlbum.getGenre());
	}
	
	@ Test
	void testSongList() {
		Album spinsAlbum = new Album ("Chicken Wings and Fries", "Crispy");
		spinsAlbum.setYear(1996);
		spinsAlbum.setGenre("Expeirmental Hip-Hop");
		
		assertEquals(0, spinsAlbum.getSongList().size());
		
		Song supaSauce = new Song("Supa Sauce", "Crispy");
		spinsAlbum.addSong(supaSauce);
		assertEquals(1, spinsAlbum.getSongList().size());
		
		Song napkin = new Song("Wheres My Napkin?", "Crispy");
		spinsAlbum.addSong(napkin);
		
		Song lick = new Song("Lick My Finger", "Crispy");
		spinsAlbum.addSong(lick);
		
		Song tShirt = new Song("T-Shirt Stain", "Crispy");
		spinsAlbum.addSong(tShirt);
		
		assertEquals(4, spinsAlbum.getSongList().size());
		
		Song ice = new Song("Ice in the Deep Fryer", "Crispy");
		spinsAlbum.addSong(ice);
		
		Song moist = new Song("Moist Towelette", "Crispy");
		spinsAlbum.addSong(moist);
		
		assertEquals(6, spinsAlbum.getSongList().size());
	}
	
	

}
