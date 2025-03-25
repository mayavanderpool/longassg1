package model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/*CONSTRUCTORS */
public class UserLibrary {
	private String username;
	private ArrayList<String> songTitles;
	private ArrayList<String> albumTitles;
	private ArrayList<String> playlistNames;
	private ArrayList<Map<String, Object>> playlists;
	private ArrayList<String> favorites;
	private ArrayList<Map<String, Object>> songRatings;

	private static final String LIBRARIES_FILE = "UserLibraries.json";
	private static Map<String, UserLibrary> userLibraries = new HashMap<>();

	public UserLibrary() {
		this.songTitles = new ArrayList<>();
		this.albumTitles = new ArrayList<>();
		this.playlistNames = new ArrayList<>();
		this.playlists = new ArrayList<>();
		this.favorites = new ArrayList<>();
		this.songRatings = new ArrayList<>();
	}

	public UserLibrary(String username) {
		this();
		this.username = username;
	}

	// loadLibraries() - loads the user libraries from Json
	@JsonIgnore
	public static void loadLibraries() {
		File file = new File(LIBRARIES_FILE);
		if (file.exists()) {
			try {
				ObjectMapper mapper = new ObjectMapper();
				userLibraries = mapper.readValue(file, new TypeReference<Map<String, UserLibrary>>() {
				});
			} catch (IOException e) {
				System.err.println("Error loading user libraries: " + e.getMessage());
			}
		}
	}

	// saveLibraries() - saves the user libraries from memory to the Json file
	@JsonIgnore
	public static void saveLibraries() {
		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.writerWithDefaultPrettyPrinter().writeValue(new File(LIBRARIES_FILE), userLibraries);
		} catch (IOException e) {
			System.err.println("Error saving user libraries: " + e.getMessage());
		}
	}

	// saveUserLibrary(String, LibraryModel) - saves a user specific library to Json
	// file
	@JsonIgnore
	public static void saveUserLibrary(String username, LibraryModel model) {

		if (userLibraries.isEmpty()) {
			loadLibraries();
		}

		if (!userLibraries.containsKey(username)) {
			userLibraries.put(username, new UserLibrary(username));
		}

		UserLibrary userLibrary = userLibraries.get(username);

		userLibrary.updateFromModel(model);

		saveLibraries();
	}

	// loadUserLibrary(String, LibraryModel) - loads a specific user's library from
	// Json
	@JsonIgnore
	public static void loadUserLibrary(String username, LibraryModel model) {

		if (userLibraries.isEmpty()) {
			loadLibraries();
		}

		if (userLibraries.containsKey(username)) {
			UserLibrary userLibrary = userLibraries.get(username);
			userLibrary.loadIntoModel(model);
		}
	}

	// updateFromModel(LibraryModel model) - updates user library
	@JsonIgnore
	private void updateFromModel(LibraryModel model) {

		songTitles.clear();
		albumTitles.clear();
		playlistNames.clear();
		playlists.clear();
		favorites.clear();

		for (Song song : model.getSongs()) {
			songTitles.add(song.getTitle());
		}

		for (Album album : model.getAlbums()) {
			albumTitles.add(album.getTitle());
		}

		playlistNames = model.getPlaylists();

		for (String playlistName : playlistNames) {
			PlayList playlist = model.getPlayList(playlistName);
			if (playlist != null) {
				Map<String, Object> playlistMap = new HashMap<>();
				playlistMap.put("name", playlistName);

				ArrayList<String> songList = new ArrayList<>();
				for (Song song : playlist.getPlaylist()) {
					songList.add(song.getTitle());
				}

				playlistMap.put("songs", songList);
				playlists.add(playlistMap);
			}
		}

		for (Song song : model.getFavorites()) {
			favorites.add(song.getTitle());
		}

		songRatings.clear();
		for (Song song : model.getSongs()) {
			if (song.getRating() > 0) {
				Map<String, Object> ratingMap = new HashMap<>();
				ratingMap.put("title", song.getTitle());
				ratingMap.put("artist", song.getArtist());
				ratingMap.put("rating", song.getRating());
				songRatings.add(ratingMap);
			}
		}
	}

	// loadIntoModel(LibraryModel) - loads data from user library into model
	@JsonIgnore
	private void loadIntoModel(LibraryModel model) {

		for (String songTitle : songTitles) {
			model.addSong(songTitle);
		}

		for (String albumTitle : albumTitles) {
			model.addAlbum(albumTitle);
		}

		for (Map<String, Object> playlistMap : playlists) {
			String name = (String) playlistMap.get("name");
			ArrayList<String> songList = (ArrayList<String>) playlistMap.get("songs");

			if (!model.getPlaylists().contains(name)) {
				PlayList newPlaylist = new PlayList(name);
				model.addPlaylist(newPlaylist);
			}

			PlayList playlist = model.getPlayList(name);
			for (String songTitle : songList) {
				ArrayList<Song> songs = model.getSong(songTitle);
				for (Song song : songs) {
					playlist.addSong(song);
				}
			}
		}

		for (String songTitle : favorites) {
			ArrayList<Song> songs = model.getSong(songTitle);
			for (Song song : songs) {
				song.setFavorite();
				model.addFavorite(song);
			}
		}

		for (Map<String, Object> ratingMap : songRatings) {
			String songTitle = (String) ratingMap.get("title");
			int rating = (Integer) ratingMap.get("rating");
			ArrayList<Song> songs = model.getSong(songTitle);
			for (Song song : songs) {
				song.setRating(rating);
			}
		}
	}

	/* GETTERS AND SETTERS */
	@JsonProperty
	public String getUsername() {
		return username;
	}

	@JsonProperty
	public void setUsername(String username) {
		this.username = username;
	}

	@JsonProperty
	public ArrayList<String> getSongTitles() {
		return songTitles;
	}

	@JsonProperty
	public void setSongTitles(ArrayList<String> songTitles) {
		this.songTitles = songTitles;
	}

	@JsonProperty
	public ArrayList<String> getAlbumTitles() {
		return albumTitles;
	}

	@JsonProperty
	public void setAlbumTitles(ArrayList<String> albumTitles) {
		this.albumTitles = albumTitles;
	}

	@JsonProperty
	public ArrayList<String> getPlaylistNames() {
		return playlistNames;
	}

	@JsonProperty
	public void setPlaylistNames(ArrayList<String> playlistNames) {
		this.playlistNames = playlistNames;
	}

	@JsonProperty
	public ArrayList<Map<String, Object>> getPlaylists() {
		return playlists;
	}

	@JsonProperty
	public void setPlaylists(ArrayList<Map<String, Object>> playlists) {
		this.playlists = playlists;
	}

	@JsonProperty
	public ArrayList<String> getFavorites() {
		return favorites;
	}

	@JsonProperty
	public void setFavorites(ArrayList<String> favorites) {
		this.favorites = favorites;
	}

	@JsonProperty
	public ArrayList<Map<String, Object>> getSongRatings() {
		return songRatings;
	}

	@JsonProperty
	public void setSongRatings(ArrayList<Map<String, Object>> songRatings) {
		this.songRatings = songRatings;
	}
}