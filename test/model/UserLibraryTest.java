package model;


/*
* File: userLibraryModelTest.java
* Author: Rees Hart and Maya Vanderpool
* Purpose: This test file tests the various methods in the userLibraryModel.java class
*  and acheives 90% (or greater) code coverage.  
*/

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

class UserLibraryTest {
    
    private static final String LIBRARIES_FILE = "UserLibraries.json";
    
    private void setup() throws Exception {
        Files.deleteIfExists(Paths.get(LIBRARIES_FILE));
        
        Field librariesField = UserLibrary.class.getDeclaredField("userLibraries");
        librariesField.setAccessible(true);
        Map<String, UserLibrary> libraries = (Map<String, UserLibrary>) librariesField.get(null);
        libraries.clear();
    }
    
    private void clean() throws Exception {
        Files.deleteIfExists(Paths.get(LIBRARIES_FILE));
    }
    
    @Test
    void ConstructorTest() throws Exception {
        setup();
        
        UserLibrary library = new UserLibrary("testUser");
        
        assertEquals("testUser", library.getUsername());
        assertNotNull(library.getSongTitles());
        assertTrue(library.getSongTitles().isEmpty());
        assertNotNull(library.getAlbumTitles());
        assertTrue(library.getAlbumTitles().isEmpty());
        
        clean();
    }
    
    @Test
    void setGetTest() throws Exception {
        setup();
        
        UserLibrary library = new UserLibrary();
        
        library.setUsername("newUser");
        assertEquals("newUser", library.getUsername());
        
        ArrayList<String> songs = new ArrayList<>();
        songs.add("Song1");
        library.setSongTitles(songs);
        assertEquals(1, library.getSongTitles().size());
        assertEquals("Song1", library.getSongTitles().get(0));
        
        ArrayList<String> favs = new ArrayList<>();
        favs.add("FavSong");
        library.setFavorites(favs);
        assertEquals(1, library.getFavorites().size());
        assertEquals("FavSong", library.getFavorites().get(0));
        
        clean();
    }
    
    @Test
    void saveLibrariesTest() throws Exception {
        setup();
        
        Field librariesField = UserLibrary.class.getDeclaredField("userLibraries");
        librariesField.setAccessible(true);
        Map<String, UserLibrary> libraries = new HashMap<>();
        UserLibrary library = new UserLibrary("testUser");
        libraries.put("testUser", library);
        librariesField.set(null, libraries);
        
        UserLibrary.saveLibraries();
        
        File file = new File(LIBRARIES_FILE);
        assertTrue(file.exists());
        
        libraries.clear();
        UserLibrary.loadLibraries();
        
        Map<String, UserLibrary> loadedLibraries = (Map<String, UserLibrary>) librariesField.get(null);
        assertTrue(loadedLibraries.containsKey("testUser"));
        assertEquals("testUser", loadedLibraries.get("testUser").getUsername());
        
        clean();
    }
    
    @Test
    void saveUserTest() throws Exception {
        setup();
        
        String username = "testUser";
        LibraryModel model = new LibraryModel();
        
        Song song = new Song("TestSong", "TestArtist", null);
        model.addSong(song.getTitle());
        
        Album album = new Album("TestAlbum", "TestArtist");
        model.addAlbum(album.getTitle());
        
        PlayList playlist = new PlayList("TestPlaylist");
        playlist.addSong(song);
        model.addPlaylist(playlist);
        
        song.setFavorite();
        model.addFavorite(song);
        
        song.setRating(5);
        
        UserLibrary.saveUserLibrary(username, model);
        
        ObjectMapper mapper = new ObjectMapper();
        Map<String, UserLibrary> savedLibraries = mapper.readValue(new File(LIBRARIES_FILE), 
                new TypeReference<Map<String, UserLibrary>>() {});
        
        assertTrue(savedLibraries.containsKey(username));
        UserLibrary library = savedLibraries.get(username);
        
        assertEquals(1, library.getSongTitles().size());
        assertEquals("TestSong", library.getSongTitles().get(0));
        
        assertEquals(1, library.getAlbumTitles().size());
        assertEquals("TestAlbum", library.getAlbumTitles().get(0));
        
        clean();
    }
    
    @Test
    void loadUserTest() throws Exception {
        setup();
        
        String username = "testUser";
        UserLibrary library = new UserLibrary(username);
        
        library.getSongTitles().add("TestSong");
        library.getAlbumTitles().add("TestAlbum");
        library.getPlaylistNames().add("TestPlaylist");
        
        Map<String, Object> playlistMap = new HashMap<>();
        playlistMap.put("name", "TestPlaylist");
        ArrayList<String> songList = new ArrayList<>();
        songList.add("TestSong");
        playlistMap.put("songs", songList);
        library.getPlaylists().add(playlistMap);
        
        library.getFavorites().add("TestSong");
        
        Map<String, Object> ratingMap = new HashMap<>();
        ratingMap.put("title", "TestSong");
        ratingMap.put("artist", "TestArtist");
        ratingMap.put("rating", 5);
        library.getSongRatings().add(ratingMap);
        
        Field librariesField = UserLibrary.class.getDeclaredField("userLibraries");
        librariesField.setAccessible(true);
        Map<String, UserLibrary> libraries = new HashMap<>();
        libraries.put(username, library);
        librariesField.set(null, libraries);
        
        LibraryModel model = new LibraryModel();
        Song testSong = new Song("TestSong", "TestArtist", null);
        model.addSong(testSong);
        
        UserLibrary.loadUserLibrary(username, model);
        
        assertEquals(1, model.getAlbums().size());
        assertEquals("TestAlbum", model.getAlbums().get(0).getTitle());
        
        assertTrue(model.getPlaylists().contains("TestPlaylist"));
        
        PlayList loadedPlaylist = model.getPlayList("TestPlaylist");
        assertNotNull(loadedPlaylist);
        assertEquals(1, loadedPlaylist.getPlaylist().size());
        
        clean();
    }
    
    @Test
    void multipleSongsTest() throws Exception {
        setup();
        
        String username = "testUser";
        LibraryModel model = new LibraryModel();
        
        Song song1 = new Song("Song1", "Artist1", null);
        Song song2 = new Song("Song2", "Artist2", null);
        model.addSong(song1.getTitle());
        model.addSong(song2.getTitle());
        
        song1.setRating(4);
        song2.setRating(5);
        
        UserLibrary.saveUserLibrary(username, model);
        
        ObjectMapper mapper = new ObjectMapper();
        Map<String, UserLibrary> savedLibraries = mapper.readValue(new File(LIBRARIES_FILE), 
            new TypeReference<Map<String, UserLibrary>>() {});
        
        UserLibrary library = savedLibraries.get(username);
        assertEquals(2, library.getSongTitles().size());
        assertEquals(2, library.getSongRatings().size());
        
        clean();
    }
}