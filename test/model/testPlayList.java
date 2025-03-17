package model;

/*
 * File: testPlayList.java
 * Author: Maya Vanderpool
 * Purpose: This is a test class for PlayList.java.
 */
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

class testPlayList {

    @Test
    void testName() {
        PlayList list = new PlayList("fun");
        assertEquals(list.getName(), "fun");
    }

    @Test
    void testAddRemove() {
        PlayList list = new PlayList("fun");
        MusicStore store = new MusicStore();
        
		Song song1 = store.getSongList().get(0);
		Song song2 = store.getSongList().get(1);
		
        list.addSong(song1);
        list.addSong(song2);
        
        
        assertEquals(2, list.getPlaylist().size());
        list.removeSong(song1);
        assertEquals(1, list.getPlaylist().size());
    }
    
    @Test
    void testGetPlaylist() {
        PlayList list = new PlayList("fun");
        MusicStore store = new MusicStore();
        
		Song song1 = store.getSongList().get(0);
        list.addSong(song1);
        
        ArrayList<Song> songs = list.getPlaylist();
        assertEquals(1, songs.size());
       
    }
    
    @Test
    void testDeepCopy() {
    	 PlayList list = new PlayList("fun");
    	 Album album = new Album("rees", "hart");
    	 Song song = new Song("r", "h", album);
    	 list.addSong(song);
    	 PlayList copy = list.deepCopy();
    	 assertEquals(list.getName(), copy.getName());
    	 assertEquals(list.getPlaylist().size(), copy.getPlaylist().size());
    }
    
    
}
    