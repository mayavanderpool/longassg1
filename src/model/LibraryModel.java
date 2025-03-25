package model;

import java.util.ArrayList;
import java.util.Stack;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/*
 * File: LibraryModel.java
 * Author: Maya Vanderpool and Rees Hart
 * Purpose: This is a test class for Song.java.
 */

public class LibraryModel {

	/* INSTANCE VARIABLES */
	private ArrayList<Song> songs;
	private ArrayList<PlayList> playLists;
	private ArrayList<Album> albums;
	private ArrayList<String> artists;
	private ArrayList<Song> favorites;
	private Stack<Song> played;
	private PlayList favSongs;
	private PlayList topRated;
	private PlayList recentPlays;
	private PlayList mostPlayed;

	/*CONSTRUCTOR */
	public LibraryModel() {
		this.songs = new ArrayList<Song>();
		this.playLists = new ArrayList<PlayList>();
		this.albums = new ArrayList<Album>();
		this.artists = new ArrayList<String>();
		this.favorites = new ArrayList<Song>();
		this.played = new Stack<Song>();
		this.favSongs = new PlayList("Favorites");
		this.topRated = new PlayList("Top Rated");
		this.recentPlays = new PlayList("Recent Plays");
		this.mostPlayed = new PlayList("Most Played");

	}

	/* GETTER */
	public Stack<Song> getPlayed() {
		return played;
	}

	// addSong(String song) - returns a boolean whether or not to add a song
	public boolean addSong(String song) {
		MusicStore store = new MusicStore();
		boolean found = false;
		for (Song s : store.getSongList()) {
			if (song.equals(s.getTitle())) {
				found = true;
				songs.add(s);
				if (!artists.contains(s.getArtist())) {
					artists.add(s.getArtist());
				}
				if (!albums.contains(s.getAlbum())) {
					albums.add(s.getAlbum());
				}
			}
		}
		return found;
	}

	// removeSong(String song) - remove inputted song from library
	public boolean removeSong(String song) {
		boolean found = false;

		for (int i = songs.size() - 1; i >= 0; i--) {
			if (song.equals(songs.get(i).getTitle())) {
				found = true;
				songs.remove(i);
			}
		}
		return found;
	}

	// removeAlbum(String album) _ remove inputted album from library
	public boolean removeAlbum(String album) {
		boolean found = false;

		for (int i = albums.size() - 1; i >= 0; i--) {
			if (album.equals(albums.get(i).getTitle())) {
				found = true;
				for (Song s : albums.get(i).getSongList()) {
					removeSong(s.getTitle());
				}
				albums.remove(i);
			}
		}
		return found;
	}

	// addPlaylist(PlayList playlist) - adds a playlist the arraylist of playlists
	public void addPlaylist(PlayList playlist) {
		playLists.add(playlist);
	}

	// addAlbum(String album) - adds an album to the library returns a boolean
	public boolean addAlbum(String album) {
		MusicStore store = new MusicStore();
		boolean found = false;
		for (Album a : store.getAlbumList()) {
			if (album.equals(a.getTitle())) {
				found = true;

				albums.add(a.deepCopy());
				for (Song s : a.getSongList()) {
					songs.add(s);
				}
				if (!artists.contains(a.getArtist())) {
					artists.add(a.getArtist());
				}
			}
		}
		return found;
	}

	// shuffleSongs() - shuffle the songs in library
	public ArrayList<Song> shuffleSongs() {
		Collections.shuffle(songs);
		return songs;
	}

	// shufflePlayLists(String name) - shuffles the songs in a playlist
	public ArrayList<Song> shufflePlayLists(String name) {
		PlayList list = getPlayList(name);
		ArrayList<Song> listSongs = list.getPlaylist();
		Collections.shuffle(listSongs);
		return listSongs;
	}

	// getSong(String title) - adds a song to the arraylist of songs
	public ArrayList<Song> getSong(String title) {
		ArrayList<Song> matches = new ArrayList<Song>();
		for (Song s : songs) {
			if (s.getTitle().equals(title)) {
				matches.add(s);
			}
		}
		return matches;
	}

	// PlayList(String name) - returns a deep copy of the a PlayList object
	public PlayList getPlayList(String name) {
		if (name.equals("Top Rated")) {
			return topRated();
		}

		for (PlayList list : playLists) {
			if (list.getName().equals(name)) {
				return list;

			}
		}
		System.out.println("\nThis playlist does not exist in the library.\n");
		return null;
	}

	// searchSongsByArtist(String artist) - returns a String of all the songs by
	// given artist
	public String searchSongsByArtist(String artist) {
		boolean found = false;
		String out = "";
		for (Song s : songs) {
			if (s.getArtist().equals(artist)) {
				found = true;
				out += s.printSong();
			}
		}
		if (found == false) {
			out += "\n This song does not exist in the library.\n";
		}
		return out;
	}

	// searchAlbumByTitle(String title) - returns a string of the albums with the
	// given title
	public ArrayList<Album> searchAlbumByTitle(String title) {
		ArrayList<Album> matches = new ArrayList<Album>();
		for (Album a : albums) {
			if (a.getTitle().equals(title)) {
				matches.add(a);
			}
		}
		return matches;
	}

	// searchAlbumByArtist(String artist) - returns a string of the albums by the
	// given artist
	public ArrayList<Album> searchAlbumByArtist(String artist) {
		ArrayList<Album> matches = new ArrayList<Album>();
		for (Album a : albums) {
			if (a.getArtist().equals(artist)) {
				matches.add(a);
			}
		}
		return matches;
	}

	// getSongs() - returns an arraylist of songs from the library
	public ArrayList<Song> getSongs() {
		ArrayList<Song> all = new ArrayList<Song>();
		for (Song s : songs) {
			all.add(s);
		}
		return all;
	}

	// getPlaylists() - returns an arraylist of strings from the library
	public ArrayList<String> getPlaylists() {
		if (!playLists.contains(topRated)) {
			addPlaylist(topRated);
		}
		
		recentPlays();
		mostPlayed();
		genrePlaylists();

		topRated();

		ArrayList<String> list = new ArrayList<>();
		for (PlayList play : playLists) {
			list.add(play.getName());

		}
		return list;
	}

	// recentPlays() - adds songs to recently played playlist
	public void recentPlays() {
		if (!playLists.contains(recentPlays)) {
			addPlaylist(recentPlays);
		}else {
			for(Song s : recentPlays.getPlaylist()){
				recentPlays.removeSong(s);
			}
		}
		while (recentPlays.getPlaylist().size() <= 10 && !played.empty()) {
			recentPlays.addSong(played.pop());
		}
	}

	// genrePlaylists() - makes a hashmap for genre playlist
	public void genrePlaylists() {
		Map<String, Integer> genres = new HashMap<>();
		for (Song s : songs) {
			String genre = s.getGenre();
			genres.put(genre, genres.getOrDefault(genre, 0) + 1);
		}
		for (Map.Entry<String, Integer> entry : genres.entrySet()) {
			if (entry.getValue() > 10) {
				makeGenrePlay(entry.getKey());
			}
		}
	}

	// makeGenrePlay(String genre) - generates automatic playlist for any genre that
	// has at least 10 songs
	public void makeGenrePlay(String genre) {
		PlayList newG = new PlayList(genre);
		addPlaylist(newG);
		for (Song s : songs) {
			if (s.getGenre().compareTo(genre) == 0) {
				newG.addSong(s);
			}
		}
	}

	// mostPlayed() - adds most frequently played playlist to playlists
	public void mostPlayed() {
		if (!playLists.contains(mostPlayed)) {
			addPlaylist(mostPlayed);
		} else {
			for (Song s : mostPlayed.getPlaylist()) {
				mostPlayed.removeSong(s);
			}
		}
		sortSongPlays();
		for (Song s : songs) {
			if (mostPlayed.getPlaylist().size() <= 10 && s.getPlays() != 0) {
				mostPlayed.addSong(s);
			}
		}
	}

	// getFavorites() - returns an arraylist of songs of the favorited songs
	public ArrayList<Song> getFavorites() {
		return this.favorites;
	}

	// favSongs(Song song) - creates a playlist of favorite songs
	public PlayList favSongs(Song song) {
		if (!playLists.contains(favSongs)) {
			addPlaylist(favSongs);
		}
		favSongs.addSong(song);

		return favSongs.deepCopy();
	}

	// topRated() - creates playlist of top rated songs
	public PlayList topRated() {
		if (!playLists.contains(topRated)) {
			addPlaylist(topRated);
		}
		for (Song s : songs) {
			if (s.getRating() >= 4 && !(topRated.getPlaylist().contains(s))) {
				topRated.addSong(s);
			}
		}
		return topRated.deepCopy();
	}

	// getArtists() - returns an arraylist of strings of the artists in the library
	public ArrayList<String> getArtists() {
		ArrayList<String> list = new ArrayList<>();
		for (String artist : artists) {
			list.add(artist);
		}
		return list;
	}

	// getAlbums() - return an arraylist of albums of the albums in the library
	public ArrayList<Album> getAlbums() {
		ArrayList<Album> list = new ArrayList<>();
		for (Album album : albums) {
			list.add(album.deepCopy());
		}
		return list;
	}

	// addPlay(Song song) - pushes song on to the stack
	public void addPlay(Song song) {
		played.push(song);
	}

	// addFavorite(Song song) - add song to favorites
	public void addFavorite(Song song) {
		favorites.add(song);
		favSongs(song);
	}

	// searchSongByGenre(String genre) - allows user to search for song by genre
	public String searchSongByGenre(String genre) {
		boolean found = false;
		String out = "";
		for (Song s : this.songs) {
			if (s.getGenre().equals(genre)) {
				found = true;
				out += s.printSong();
			}
		}
		if (found == false) {
			out += "\nThis genre does not exist in the library.\n";
		}
		return out;
	}

	// SortSongsTitle() - allows user to sort songs in library by title
	public void sortSongsTitle() {
		int j = songs.size();
		boolean swap = false;
		do {
			swap = false;
			for (int i = 0; i < j - 1; i++) {
				if (songs.get(i).getTitle().compareTo(songs.get(i + 1).getTitle()) > 0) {
					Song temp = new Song(songs.get(i));
					songs.set(i, songs.get(i + 1));
					songs.set(i + 1, temp);
					swap = true;
				}
			}
			j--;
		} while (swap);
	}

	// SortSongsArtist() - allows user to sort songs in library by artist
	public void sortSongsArtist() {
		int j = songs.size();
		boolean swap = false;
		do {
			swap = false;
			for (int i = 0; i < j - 1; i++) {
				if (songs.get(i).getArtist().compareTo(songs.get(i + 1).getArtist()) > 0) {
					Song temp = new Song(songs.get(i));
					songs.set(i, songs.get(i + 1));
					songs.set(i + 1, temp);
					swap = true;
				}
			}
			j--;
		} while (swap);
	}

	// sortSongsRating() - allows user to sort songs in library by rating
	public void sortSongsRating() {
		int j = songs.size();
		boolean swap = false;
		do {
			swap = false;
			for (int i = 0; i < j - 1; i++) {
				if (songs.get(i).getRating() < songs.get(i + 1).getRating()) {
					Song temp = new Song(songs.get(i));
					songs.set(i, songs.get(i + 1));
					songs.set(i + 1, temp);
					swap = true;
				}
			}
			j--;
		} while (swap);
	}

	// sortSongPlays() - allows user to sort songs in library by frequency played
	public void sortSongPlays() {
		int j = songs.size();
		boolean swap = false;
		do {
			swap = false;
			for (int i = 0; i < j - 1; i++) {
				if (songs.get(i).getPlays() < songs.get(i + 1).getPlays()) {
					Song temp = new Song(songs.get(i));
					songs.set(i, songs.get(i + 1));
					songs.set(i + 1, temp);
					swap = true;
				}
			}
			j--;
		} while (swap);
	}

}
