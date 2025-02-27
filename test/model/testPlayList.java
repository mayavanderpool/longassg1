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
        PlayList list = new PlayList();

        assertEquals(list.getName(), null);
        list.setName("fun");
        assertEquals(list.getName(), "fun");
    }

    @Test
    void testAdd() {
        PlayList list = new PlayList();
        MusicStore store = new MusicStore();
		Song song1 = store.getSongList().get(0);
        list.addSong(song1);
        assertEquals(1, list.getPlaylist().size());

    }
}
