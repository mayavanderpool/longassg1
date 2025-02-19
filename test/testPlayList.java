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
        Song song1 = new Song("Red Wine Supernova", "Chappelle Roan");

        list.addSong("Red Wine Supernova", "Chappelle Roan");
        assertEquals(1, list.size());

    }
}
