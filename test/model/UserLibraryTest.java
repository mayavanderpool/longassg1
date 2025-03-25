package model;

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
    void ConstructorTest1() throws Exception {
        setup();
        
        UserLibrary library = new UserLibrary();
        
        assertNotNull(library.getSongTitles());
        assertTrue(library.getSongTitles().isEmpty());
        assertNotNull(library.getAlbumTitles());
        assertTrue(library.getAlbumTitles().isEmpty());
        assertNotNull(library.getPlaylistNames());
        assertTrue(library.getPlaylistNames().isEmpty());
        assertNotNull(library.getPlaylists());
        assertTrue(library.getPlaylists().isEmpty());
        assertNotNull(library.getFavorites());
        assertTrue(library.getFavorites().isEmpty());
        assertNotNull(library.getSongRatings());
        assertTrue(library.getSongRatings().isEmpty());
        
        clean();
    }
    
    @Test
    void ConstructorTest2() throws Exception {
        setup();
        
        UserLibrary library = new UserLibrary("testUser");
        
        assertEquals("testUser", library.getUsername());
        assertNotNull(library.getSongTitles());
        assertTrue(library.getSongTitles().isEmpty());
        
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
        
        ArrayList<String> albums = new ArrayList<>();
        albums.add("Album1");
        library.setAlbumTitles(albums);
        assertEquals(1, library.getAlbumTitles().size());
        assertEquals("Album1", library.getAlbumTitles().get(0));
        
        ArrayList<String> plNames = new ArrayList<>();
        plNames.add("Playlist1");
        library.setPlaylistNames(plNames);
        assertEquals(1, library.getPlaylistNames().size());
        assertEquals("Playlist1", library.getPlaylistNames().get(0));
        
        ArrayList<Map<String, Object>> playlists = new ArrayList<>();
        Map<String, Object> playlist = new HashMap<>();
        playlist.put("name", "Playlist1");
        playlist.put("songs", new ArrayList<String>());
        playlists.add(playlist);
        library.setPlaylists(playlists);
        assertEquals(1, library.getPlaylists().size());
        
        ArrayList<String> favs = new ArrayList<>();
        favs.add("FavSong");
        library.setFavorites(favs);
        assertEquals(1, library.getFavorites().size());
        assertEquals("FavSong", library.getFavorites().get(0));
        
        ArrayList<Map<String, Object>> ratings = new ArrayList<>();
        Map<String, Object> rating = new HashMap<>();
        rating.put("title", "RatedSong");
        rating.put("artist", "Artist");
        rating.put("rating", 5);
        ratings.add(rating);
        library.setSongRatings(ratings);
        assertEquals(1, library.getSongRatings().size());
        
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
        
        assertEquals(1, library.getPlaylistNames().size());
        assertEquals("TestPlaylist", library.getPlaylistNames().get(0));
        
        assertEquals(1, library.getFavorites().size());
        assertEquals("TestSong", library.getFavorites().get(0));
        
        assertEquals(1, library.getSongRatings().size());
        assertEquals("TestSong", library.getSongRatings().get(0).get("title"));
        assertEquals(5, library.getSongRatings().get(0).get("rating"));
        
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
        assertEquals("TestSong", loadedPlaylist.getPlaylist().get(0).getTitle());
        
        assertTrue(testSong.getFavorite());
        
        assertEquals(5, testSong.getRating());
        
        clean();
    }
    
    @Test
    void emptyLoadTest() throws Exception {
        setup();
        
        UserLibrary.loadLibraries();
        
        Field librariesField = UserLibrary.class.getDeclaredField("userLibraries");
        librariesField.setAccessible(true);
        Map<String, UserLibrary> libraries = (Map<String, UserLibrary>) librariesField.get(null);
        
        assertTrue(libraries.isEmpty());
        
        clean();
    }
    
    @Test
    void loadNoUserTest() throws Exception {
        setup();
        
        String username = "nonExistentUser";
        LibraryModel model = new LibraryModel();
        
        UserLibrary.loadUserLibrary(username, model);
        
        clean();
    }
    
    @Test
    void loadIOTest() throws Exception {
        setup();
        
        File dbDir = new File(LIBRARIES_FILE);
        dbDir.mkdir();
        
        try {
            UserLibrary.loadLibraries();
            
            Field librariesField = UserLibrary.class.getDeclaredField("userLibraries");
            librariesField.setAccessible(true);
            Map<String, UserLibrary> libraries = (Map<String, UserLibrary>) librariesField.get(null);
            assertTrue(libraries.isEmpty());
        } finally {
            dbDir.delete();
            clean();
        }
    }
    
    @Test
    void saveIOTest() throws Exception {
        setup();
        
        File dbDir = new File(LIBRARIES_FILE);
        dbDir.mkdir();
        
        try {
            Field librariesField = UserLibrary.class.getDeclaredField("userLibraries");
            librariesField.setAccessible(true);
            Map<String, UserLibrary> libraries = new HashMap<>();
            libraries.put("testUser", new UserLibrary("testUser"));
            librariesField.set(null, libraries);
            
            UserLibrary.saveLibraries();
        } finally {
            dbDir.delete();
            clean();
        }
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
    
    @Test
    void multipleItemsTest() throws Exception {
        setup();
        
        String username = "testUser";
        UserLibrary library = new UserLibrary(username);
        
        library.getSongTitles().add("Song1");
        library.getSongTitles().add("Song2");
        
        ArrayList<Map<String, Object>> playlists = new ArrayList<>();
        Map<String, Object> playlist = new HashMap<>();
        playlist.put("name", "Playlist1");
        ArrayList<String> songList = new ArrayList<>();
        songList.add("Song1");
        songList.add("Song2");
        playlist.put("songs", songList);
        playlists.add(playlist);
        library.setPlaylists(playlists);
        
        library.getFavorites().add("Song1");
        library.getFavorites().add("Song2");
        
        ArrayList<Map<String, Object>> ratings = new ArrayList<>();
        Map<String, Object> rating1 = new HashMap<>();
        rating1.put("title", "Song1");
        rating1.put("artist", "Artist1");
        rating1.put("rating", 4);
        ratings.add(rating1);
        
        Map<String, Object> rating2 = new HashMap<>();
        rating2.put("title", "Song2");
        rating2.put("artist", "Artist2");
        rating2.put("rating", 5);
        ratings.add(rating2);
        library.setSongRatings(ratings);
        
        Field librariesField = UserLibrary.class.getDeclaredField("userLibraries");
        librariesField.setAccessible(true);
        Map<String, UserLibrary> libraries = new HashMap<>();
        libraries.put(username, library);
        librariesField.set(null, libraries);
        
        LibraryModel model = new LibraryModel();
        Song testSong1 = new Song("Song1", "Artist1", null);
        Song testSong2 = new Song("Song2", "Artist2", null);
        model.addSong(testSong1.getTitle());
        model.addSong(testSong2.getTitle());
        
        PlayList emptyPlaylist = new PlayList("Playlist1");
        model.addPlaylist(emptyPlaylist);
        
        UserLibrary.loadUserLibrary(username, model);
        
        PlayList loadedPlaylist = model.getPlayList("Playlist1");
        assertNotNull(loadedPlaylist);
        assertEquals(2, loadedPlaylist.getPlaylist().size());
        
        assertTrue(testSong1.getFavorite());
        assertTrue(testSong2.getFavorite());
        
        assertEquals(4, testSong1.getRating());
        assertEquals(5, testSong2.getRating());
        
        clean();
    }
    
    @Test
    void updateFromModelTest() throws Exception {
        setup();
        
        String username = "testUser";
        LibraryModel model = new LibraryModel();
        
        Song song1 = new Song("Song1", "Artist1", null);
        Song song2 = new Song("Song2", "Artist2", null);
        model.addSong(song1.getTitle());
        model.addSong(song2.getTitle());
        
        Album album1 = new Album("Album1", "Artist1");
        Album album2 = new Album("Album2", "Artist2");
        model.addAlbum(album1.getTitle());
        model.addAlbum(album2.getTitle());
        
        PlayList playlist1 = new PlayList("Playlist1");
        playlist1.addSong(song1);
        model.addPlaylist(playlist1);
        
        PlayList playlist2 = new PlayList("Playlist2");
        playlist2.addSong(song2);
        model.addPlaylist(playlist2);
        
        PlayList multiSongPlaylist = new PlayList("MultiSongs");
        multiSongPlaylist.addSong(song1);
        multiSongPlaylist.addSong(song2);
        model.addPlaylist(multiSongPlaylist);
        
        song1.setFavorite();
        song2.setFavorite();
        model.addFavorite(song1);
        model.addFavorite(song2);
        
        song1.setRating(4);
        song2.setRating(5);
        
        model.getPlaylists().add("NullPlaylist");
        
        UserLibrary.saveUserLibrary(username, model);
        
        ObjectMapper mapper = new ObjectMapper();
        Map<String, UserLibrary> savedLibraries = mapper.readValue(new File(LIBRARIES_FILE), 
            new TypeReference<Map<String, UserLibrary>>() {});
        
        UserLibrary library = savedLibraries.get(username);
        
        assertEquals(2, library.getSongTitles().size());
        assertTrue(library.getSongTitles().contains("Song1"));
        assertTrue(library.getSongTitles().contains("Song2"));
        
        assertEquals(2, library.getAlbumTitles().size());
        assertTrue(library.getAlbumTitles().contains("Album1"));
        assertTrue(library.getAlbumTitles().contains("Album2"));
        
        assertEquals(4, library.getPlaylistNames().size());
        assertTrue(library.getPlaylistNames().contains("Playlist1"));
        assertTrue(library.getPlaylistNames().contains("Playlist2"));
        assertTrue(library.getPlaylistNames().contains("MultiSongs"));
        assertTrue(library.getPlaylistNames().contains("NullPlaylist"));
        
        assertEquals(3, library.getPlaylists().size());
        
        boolean foundMultiSongs = false;
        for (Map<String, Object> playlistMap : library.getPlaylists()) {
            if ("MultiSongs".equals(playlistMap.get("name"))) {
                foundMultiSongs = true;
                ArrayList<String> playlistSongs = (ArrayList<String>) playlistMap.get("songs");
                assertEquals(2, playlistSongs.size());
                assertTrue(playlistSongs.contains("Song1"));
                assertTrue(playlistSongs.contains("Song2"));
            }
        }
        assertTrue(foundMultiSongs);
        
        assertEquals(2, library.getFavorites().size());
        assertTrue(library.getFavorites().contains("Song1"));
        assertTrue(library.getFavorites().contains("Song2"));
        
        assertEquals(2, library.getSongRatings().size());
        
        boolean foundSong1Rating = false;
        boolean foundSong2Rating = false;
        for (Map<String, Object> ratingMap : library.getSongRatings()) {
            String title = (String) ratingMap.get("title");
            if ("Song1".equals(title)) {
                foundSong1Rating = true;
                assertEquals(4, ratingMap.get("rating"));
            } else if ("Song2".equals(title)) {
                foundSong2Rating = true;
                assertEquals(5, ratingMap.get("rating"));
            }
        }
        assertTrue(foundSong1Rating);
        assertTrue(foundSong2Rating);
        
        clean();
    }
}