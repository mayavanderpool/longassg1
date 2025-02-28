package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import model.Album;
import model.Song;

/*
* File: AlbumTest.java
* Author:Rees Hart and Maya Vanderpool
* Purpose: This test files tests the Album.java class and acheives 90% (or 
* greater) code coverage
*/

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
		
		Song supaSauce = new Song("Supa Sauce", "Crispy", spinsAlbum);
		spinsAlbum.addSong(supaSauce);
		assertEquals(1, spinsAlbum.getSongList().size());
		
		Song napkin = new Song("Wheres My Napkin?", "Crispy", spinsAlbum);
		spinsAlbum.addSong(napkin);
		
		Song lick = new Song("Lick My Finger", "Crispy", spinsAlbum);
		spinsAlbum.addSong(lick);
		
		Song tShirt = new Song("T-Shirt Stain", "Crispy", spinsAlbum);
		spinsAlbum.addSong(tShirt);
		
		assertEquals(4, spinsAlbum.getSongList().size());
		
		Song ice = new Song("Ice in the Deep Fryer", "Crispy", spinsAlbum);
		spinsAlbum.addSong(ice);
		
		Song moist = new Song("Moist Towelette", "Crispy", spinsAlbum);
		spinsAlbum.addSong(moist);
		
		assertEquals(6, spinsAlbum.getSongList().size());
	}
	
	@Test 
	void testPrint() {
		Album cornAlbum = new Album ("Globalization", "Cornelius Westerfinch");
		cornAlbum.setYear(2025);
		cornAlbum.setGenre("economic policy");
		Song fragile = new Song("fragile supply chain", "Cornelius Westerfinch", cornAlbum);
		cornAlbum.addSong(fragile);
		
		assertEquals("Globalization" + "," + "Cornelius Westerfinch" + "," + "economic policy" + "," + "2025\n" + "fragile supply chain\n", cornAlbum.printAlbum());
	}
	
	@Test
	void testDeepCopy() {
		Album cornAlbum = new Album ("Globalization", "Cornelius Westerfinch");
		cornAlbum.setYear(2025);
		cornAlbum.setGenre("economic policy");
		Song fragile = new Song("fragile supply chain", "Cornelius Westerfinch", cornAlbum);
		cornAlbum.addSong(fragile);
		
		Album copy = cornAlbum.deepCopy();
		
		assertEquals(cornAlbum.getTitle(),copy.getTitle());
		assertEquals(cornAlbum.getGenre(),copy.getGenre());
		assertEquals(cornAlbum.getYear(),copy.getYear());
	}
	

}
