package model;

import java.util.ArrayList;
import java.util.Scanner;

/*
 * File: View.java
 * Author: Rees Hart and Maya Vanderpool
 * Purpose: This class represents the user interface
 */

public class View {
	private Scanner scanner;
	private LibraryModel model;
	private MusicStore store;

	public View() {
		this.model = new LibraryModel();
		this.store = new MusicStore();
		this.scanner = new Scanner(System.in);
	}

	public void run() {
		System.out.println("Welcome to your Music Library");

		boolean inputing = true;
		while (inputing) {
			displayOptions();
			System.out.println("Enter your choice: ");
			String choice = scanner.nextLine().trim();

			switch (choice) {
				case "1":
					searchMusicStore();
					break;
				case "2":
					searchLibrary();
					break;
				case "3":
					addToLibrary();
					break;
				case "4":
					seeLibrary();
					break;
				case "5":
					createPlaylist();
					break;
				case "6":
					addOrRemovePlaylist();
					break;
				case "7":
					markFav();
					break;
				case "8":
					rateSong();
					break;
				case "9":
					exit();
					break;
				default:
					System.out.println("Invalid Entry");
			}

		}
	}

	private void exit() {
		System.out.println("Exiting Music Library!");
		scanner.close();
		System.exit(0);

	}

	private void rateSong() {
		System.out.println("Enter song title: ");
		String songName = scanner.nextLine().trim();
		ArrayList<Song> songs = model.getSong(songName);
		if (songs.size() == 0) {
			System.out.println("This song does not exist in the library.");
		} else {
			for (Song s : songs) {
				System.out.println("Enter rating (1-5): ");
				String rating = scanner.nextLine().trim();
				s.setRating(Integer.parseInt(rating));
			}
		}

	}

	private void markFav() {
		System.out.println("Enter song title: ");
		String songName = scanner.nextLine().trim();
		ArrayList<Song> songs = model.getSong(songName);
		if (songs.size() == 0) {
			System.out.println("This song does not exist in the library.");
		} else {
			for (Song s : songs) {
				s.setFavorite();
			}
		}

	}

	private void addOrRemovePlaylist() {
		System.out.println("Menu: ");
		System.out.println("1 - Add songs");
		System.out.println("2 - Remove songs");
		System.out.println("Enter your choice: ");

		String choice = scanner.nextLine().trim();
		switch (choice) {
			case "1":
				System.out.println("Enter playlist name: ");
				String name = scanner.nextLine().trim();
				PlayList list = model.getPlayList(name);

				System.out.println("Enter song title: ");
				String songName = scanner.nextLine().trim();
				ArrayList<Song> songs = model.getSong(songName);
				if (songs.size() == 0) {
					System.out.println("This song does not exist in the library.");
				} else {
					for (Song s : songs) {
						list.addSong(s);
					}
				}
				break;
			case "2":
				System.out.println("Enter playlist name: ");
				String playName = scanner.nextLine().trim();
				PlayList play_list = model.getPlayList(playName);

				System.out.println("Enter song title: ");
				String songTitle = scanner.nextLine().trim();
				ArrayList<Song> songs2 = model.getSong(songName);
				if (songs2.size() == 0) {
					System.out.println("This song does not exist in the library.");
				} else {
					for (Song son : songs2) {
						play_list.removeSong(son)
					}
				}
				break;
			default:
				System.out.println("Invalid Entry");
		}

	}

	private void createPlaylist() {
		System.out.println("Enter playlist name: ");
		String name = scanner.nextLine().trim();
		PlayList newPlay = new PlayList(name);
		model.addPlaylist(newPlay);

	}

	private void seeLibrary() {
		System.out.println("Menu: ");
		System.out.println("1 - See songs");
		System.out.println("2 - See artists");
		System.out.println("3 - See albums");
		System.out.println("4 - See playlists");
		System.out.println("5 - See favorites");
		System.out.println("Enter your choice: ");

		String choice = scanner.nextLine().trim();
		switch (choice) {
			case "1":
				ArrayList<Song> songsList = model.getSongs();
				for (Song s : songsList) {
					System.out.println(s.printSong());
				}
				break;
			case "2":
				ArrayList<String> artists = model.getArtists();
				for (String art : artists) {
					System.out.println(art);
				}
				break;
			case "3":
				ArrayList<String> albums = model.getAlbums();
				for (String al : albums) {
					System.out.println(al);
				}
				break;
			case "4":
				ArrayList<String> lists = model.getPlaylists();
				for (String li : lists) {
					System.out.println(li);
				}
				break;

			case "5":
				ArrayList<String> favs = model.getPlaylists();
				for (String fav : favs) {
					System.out.println(fav);
				}
				break;
			default:
				System.out.println("Invalid Entry");

		}

	}

	private void addToLibrary() {
		System.out.println("Menu: ");
		System.out.println("1 - Add song");
		System.out.println("2 - Add album");
		System.out.println("Enter your choice: ");

		String choice = scanner.nextLine().trim();
		switch (choice) {
			case "1":
				System.out.println("Enter song title: ");
				String song = scanner.nextLine().trim();
				model.addSong(song);
				break;
			case "2":
				System.out.println("Enter album title: ");
				String album = scanner.nextLine().trim();
				model.addAlbum(album);
				break;
			default:
				System.out.println("Invalid Entry");
		}
	}

	private void searchLibrary() {
		System.out.println("Menu: ");
		System.out.println("1 - Search song by title");
		System.out.println("2 - Search song by artist");
		System.out.println("3 - Search album by title");
		System.out.println("4 - Search album by artist");
		System.out.println("5 - Search playlist");
		System.out.println("Enter your choice: ");

		String choice = scanner.nextLine().trim();

		switch (choice) {
			case "1":
				System.out.println("Enter song title: ");
				String title = scanner.nextLine().trim();
				ArrayList<Song> songs = model.getSong(title);
				if (songs.size() == 0) {
					System.out.println("This song does not exist in the library.");
				} else {
					for (Song s : songs) {
						System.out.println(s.printSong());
					}
				}
				break;
			case "2":
				System.out.println("Enter song artist: ");
				String artist = scanner.nextLine().trim();
				System.out.println(model.searchSongsByArtist(artist));
				break;
			case "3":
				System.out.println("Enter album title: ");
				String albTitle = scanner.nextLine().trim();
				System.out.println(model.searchAlbumByTitle(albTitle));
				break;
			case "4":
				System.out.println("Enter album artist: ");
				String albArtist = scanner.nextLine().trim();
				System.out.println(model.searchAlbumByArtist(albArtist));
				break;
			case "5":
				System.out.println("Enter playlist name: ");
				String name = scanner.nextLine().trim();
				PlayList playlist = model.getPlayList(name);
				if (playlist != null) {
					for (Song son : playlist.getPlaylist()) {
						System.out.println(son.printSong());
					}
				} else {
					System.out.println("Playlist not found.");
				}
				break;
			default:
				System.out.println("Invalid Entry");
		}

	}

	private void searchMusicStore() {
		System.out.println("Menu: ");
		System.out.println("1 - Search song by title");
		System.out.println("2 - Search song by artist");
		System.out.println("3 - Search album by title");
		System.out.println("4 - Search album by artist");
		System.out.println("Enter your choice: ");

		String choice = scanner.nextLine().trim();

		switch (choice) {
			case "1":
				System.out.println("Enter song title: ");
				String title = scanner.nextLine().trim();
				System.out.println(store.searchSongsByTitle(title));
				break;
			case "2":
				System.out.println("Enter song artist: ");
				String artist = scanner.nextLine().trim();
				System.out.println(store.searchSongsByArtist(artist));
				break;
			case "3":
				System.out.println("Enter album title: ");
				String albName = scanner.nextLine().trim();
				System.out.println(store.searchAlbumByTitle(albName));
				break;
			case "4":
				System.out.println("Enter album artist: ");
				String name = scanner.nextLine().trim();
				System.out.println(store.searchAlbumByArtist(name));
				break;
			default:
				System.out.println("Invalid Entry");
		}

	}

	private void displayOptions() {
		System.out.println("How can I help you today?");
		System.out.println("Menu:");

		System.out.println("1 - Search the music store");
		System.out.println("2 - Search your library");
		System.out.println("3 - Add to your library");
		System.out.println("4 - See contents of your library");
		System.out.println("5 - Create a playlist");
		System.out.println("6 - Add or remove songs from an existing playlist");
		System.out.println("7 - Mark a song as 'favorite'");
		System.out.println("8 - Rate a song");
		System.out.println("9 - EXIT");

	}

}
