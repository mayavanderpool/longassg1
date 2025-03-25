package model;

import java.util.ArrayList;
import java.util.Scanner;

/*
 * File: View.java
 * Author: Rees Hart and Maya Vanderpool
 * Purpose: This class represents the user interface
 * Use of AI: I used chatGPT to get an idea of the overall
 * format for the View class. It showed me how a couple of 
 * the commands were structured and then I wrote my 
 * own code 
 */

public class View {

	/* INSTANCE VARIABLES */
	private Scanner scanner;
	private LibraryModel model;
	private MusicStore store;
	private String currentUser;

	public View() {
		this.model = new LibraryModel();
		this.store = new MusicStore();
		this.scanner = new Scanner(System.in);
	}

	// run() - begins program
	public void run() {

		login();

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
					playSong();
					break;
				case "10":
					removeFromLib();
					break;
				case "11":
					if (logout()) {
						// If logout returns true, it means the user wants to exit completely
						inputing = false;
					}
					break;
				default:
					System.out.println("\nInvalid Entry\n");
			}
		}
	}

	// removes a song/album from library
	private void removeFromLib() {
		System.out.println("Menu: ");
		System.out.println("1 - Remove song");
		System.out.println("2 - Remove album");
		System.out.println("Enter your choice: ");

		String choice = scanner.nextLine().trim();
		switch (choice) {
			case "1":
				System.out.println("Enter song title: ");
				String song = scanner.nextLine().trim();
				boolean found = model.removeSong(song);
				if (found == true) {
					System.out.println("\nSong removed from library \n");
				} else {
					System.out.println("\nUnable to remove song \n");
				}
				break;
			case "2":
				System.out.println("Enter album title: ");
				String album = scanner.nextLine().trim();
				boolean found2 = model.removeAlbum(album);
				if (found2 == true) {
					System.out.println("\nAlbum removed from library \n");
				} else {
					System.out.println("\nUnable to remove album \n");
				}
				break;
			default:
				System.out.println("\nInvalid Entry\n");
		}
	}

	// playSong() - plays a song in the user library
	private void playSong() {
		System.out.println("Enter song title: ");
		String songName = scanner.nextLine().trim();
		ArrayList<Song> songs = model.getSong(songName);
		if (songs.size() == 0) {
			System.out.println("This song does not exist in the library.");
		} else {
			for (Song s : songs) {
				s.playSong();
				model.addPlay(s);
			}
		}
	}

	// logout() - logs a user out of the library and brings them back to login
	private boolean logout() {

		if (currentUser != null) {
			UserLibrary.saveUserLibrary(currentUser, model);
			System.out.println("\nLogging out " + currentUser + ".\n");

			currentUser = null;
			model = new LibraryModel();

			login();
			return false;
		}
		return true;
	}

	// rateSong() - allows client to rate song
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
				int num = Integer.parseInt(rating);
				if (num >= 1 && num <= 5) {
					s.setRating(num);
					if (num == 5) {
						model.addFavorite(s);
					}
					System.out.println("\n" + songName + " rated: " + num + "\n");
				} else {
					System.out.println("\nInvalid input! \n");
				}
			}
		}
	}

	// markFav() - allows client to mark a song as favorite
	private void markFav() {
		System.out.println("Enter song title: ");
		String songName = scanner.nextLine().trim();
		ArrayList<Song> songs = model.getSong(songName);
		if (songs.size() == 0) {
			System.out.println("This song does not exist in the library.");
		} else {
			for (Song s : songs) {
				s.setFavorite();
				model.addFavorite(s);
				System.out.println("\n" + songName + " has been marked favorite \n");
			}
		}
	}

	// addOrRemovePlaylist() - add or remove songs from the playlist in library
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
					System.out.println("\nThis song does not exist in the library. \n");
				} else {
					for (Song s : songs) {
						list.addSong(s);
						System.out.println("\nSong added \n");
					}
				}
				break;
			case "2":
				System.out.println("Enter playlist name: ");
				String playName = scanner.nextLine().trim();
				PlayList play_list = model.getPlayList(playName);

				System.out.println("Enter song title: ");
				String songTitle = scanner.nextLine().trim();
				ArrayList<Song> songs2 = model.getSong(songTitle);
				if (songs2.size() == 0) {
					System.out.println("This song does not exist in the library.");
				} else {
					for (Song song : songs2) {
						play_list.removeSong(song);
						System.out.println("\nSong removed \n");
					}
				}
				break;
			default:
				System.out.println("\nInvalid Entry\n");
		}
	}

	// createPlaylist() - create a new playlist
	private void createPlaylist() {
		System.out.println("Enter playlist name: ");
		String name = scanner.nextLine().trim();
		PlayList newPlay = new PlayList(name);
		model.addPlaylist(newPlay);
		System.out.println("\nNew Playlist: '" + name + "' created \n");
	}

	// seeLibrary() - view the library
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
				seeSongs();
				break;
			case "2":
				ArrayList<String> artists = model.getArtists();
				if (artists.size() != 0) {
					System.out.println("\nArtists: \n");
				} else {
					System.out.println("\nNo artists in library \n");
				}
				for (String art : artists) {
					System.out.println(art + "\n");
				}
				break;
			case "3":
				ArrayList<Album> albums = model.getAlbums();
				if (albums.size() != 0) {
					System.out.println("\nAlbums: \n");
				} else {
					System.out.println("\nNo Albums in library \n");
				}
				for (Album al : albums) {
					System.out.println(al.printAlbum() + "\n");
				}
				break;
			case "4":
				ArrayList<String> lists = model.getPlaylists();
				if (lists.size() != 0) {
					System.out.println("\nPlaylists: \n");
				} else {
					System.out.println("\nNo playlists in library \n");
				}

				for (String li : lists) {
					System.out.println(li + "\n");
				}
				break;

			case "5":
				ArrayList<Song> favs = model.getFavorites();
				if (favs.size() != 0) {
					System.out.println("\nFavorites: \n");
				} else {
					System.out.println("\nNo favorites in library \n");
				}
				for (Song fav : favs) {
					System.out.println(fav.printSong() + "\n");
				}
				break;
			default:
				System.out.println("\nInvalid Entry\n");
		}
	}

	// seeSongs() - choose how to see songs in the library outputted by different
	// sorting methods
	private void seeSongs() {
		System.out.println("See songs sorted by: ");
		System.out.println("1 - Title");
		System.out.println("2 - Artist");
		System.out.println("3 - Rating");
		System.out.println("4 - Shuffled");
		System.out.println("Enter your choice: ");

		String choice = scanner.nextLine().trim();
		switch (choice) {
			case "1":
				model.sortSongsTitle();
				ArrayList<Song> songsList = model.getSongs();
				if (songsList.size() != 0) {
					System.out.println("\nSongs: \n");
				} else {
					System.out.println("\nNo songs in library \n");
				}
				for (Song s : songsList) {
					System.out.println(s.printSong() + "\n");
				}
				break;
			case "2":
				model.sortSongsArtist();
				ArrayList<Song> songsL = model.getSongs();
				if (songsL.size() != 0) {
					System.out.println("\nSongs: \n");
				} else {
					System.out.println("\nNo songs in library \n");
				}
				for (Song s : songsL) {
					System.out.println(s.printSong() + "\n");
				}
				break;
			case "3":
				model.sortSongsRating();
				ArrayList<Song> list = model.getSongs();
				if (list.size() != 0) {
					System.out.println("\nSongs: \n");
				} else {
					System.out.println("\nNo songs in library \n");
				}
				for (Song s : list) {
					System.out.println(s.printSong() + "Rated: " + s.getRating() + "\n");
				}
				break;
			case "4":
				model.shuffleSongs();
				ArrayList<Song> shuffled = model.getSongs();
				if (shuffled.size() != 0) {
					System.out.println("\nSongs: \n");
				} else {
					System.out.println("\nNo songs in library \n");
				}
				for (Song s : shuffled) {
					System.out.println(s.printSong() + "\n");
				}
				break;
			case "5":
				model.sortSongPlays();
				ArrayList<Song> list3 = model.getSongs();
				if (list3.size() != 0) {
					System.out.println("\nSongs: \n");
				} else {
					System.out.println("\nNo songs in library \n");
				}
				for (Song s : list3) {
					System.out.println(s.printSong() + "Played: " + s.getPlays() + "\n");
				}
				break;

			default:
				System.out.println("\nInvalid Entry\n");
		}
	}

	// addtoLibrary() - add songs or albums from the music store to library
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
				boolean found = model.addSong(song);
				if (found == true) {
					System.out.println("\nSong added to library \n");
				} else {
					System.out.println("\nUnable to add song \n");
				}
				break;
			case "2":
				System.out.println("Enter album title: ");
				String album = scanner.nextLine().trim();
				boolean found2 = model.addAlbum(album);
				if (found2 == true) {
					System.out.println("\nAlbum added to library \n");
				} else {
					System.out.println("\nUnable to add album \n");
				}
				break;
			default:
				System.out.println("\nInvalid Entry\n");
		}
	}

	// searchLibrary() - search for specific songs, albums, and playlists in library
	private void searchLibrary() {
		System.out.println("Menu: ");
		System.out.println("1 - Search song by title");
		System.out.println("2 - Search song by artist");
		System.out.println("3 - Search album by title");
		System.out.println("4 - Search album by artist");
		System.out.println("5 - Search playlist");
		System.out.println("6 - Search song by genre");
		System.out.println("Enter your choice: ");

		String choice = scanner.nextLine().trim();

		switch (choice) {
			case "1":
				System.out.println("Enter song title: ");
				String title = scanner.nextLine().trim();
				ArrayList<Song> songs = model.getSong(title);
				if (songs.size() == 0) {
					System.out.println("\nThis song does not exist in the library.\n");
				} else {
					for (Song s : songs) {
						System.out.println("\n" + s.printSong() + "\n");
					}
					System.out.println("Would you like to view the album info (yes/no)\n");
					String answer = scanner.nextLine().trim();

					switch (answer) {
						case "yes":
							for (Song s : songs) {
								System.out.println("\n" + s.getAlbum().printAlbum() + "\n");
								System.out.println("This album exists in your library\n");
							}
							break;
						case "no":
							break;
					}
				}
				break;
			case "2":
				System.out.println("Enter song artist: ");
				String artist = scanner.nextLine().trim();
				System.out.println("\n" + model.searchSongsByArtist(artist) + "\n");
				break;
			case "3":
				System.out.println("Enter album title: ");
				String albTitle = scanner.nextLine().trim();
				ArrayList<Album> albumsByTitle = model.searchAlbumByTitle(albTitle);
				if (albumsByTitle.isEmpty()) {
					System.out.println("\nNo albums found with this title.\n");
				} else {
					System.out.println("\nAlbums found:\n");
					// Use a Set to track unique albums when displaying
					java.util.Set<String> uniqueAlbums = new java.util.HashSet<>();
					for (Album album : albumsByTitle) {
						// Create a unique identifier for each album
						String albumIdentifier = album.getTitle() + "," + album.getArtist();
						// Only print if this album hasn't been printed yet
						if (uniqueAlbums.add(albumIdentifier)) {
							System.out.println(album.printAlbum());
						}
					}
				}
				break;
			case "4":
				System.out.println("Enter album artist: ");
				String albArtist = scanner.nextLine().trim();
				ArrayList<Album> albumsByArtist = model.searchAlbumByArtist(albArtist);
				if (albumsByArtist.isEmpty()) {
					System.out.println("\nNo albums found by this artist.\n");
				} else {
					System.out.println("\nAlbums found:\n");
					// Use a Set to track unique albums when displaying
					java.util.Set<String> uniqueAlbums = new java.util.HashSet<>();
					for (Album album : albumsByArtist) {
						// Create a unique identifier for each album
						String albumIdentifier = album.getTitle() + "," + album.getArtist();
						// Only print if this album hasn't been printed yet
						if (uniqueAlbums.add(albumIdentifier)) {
							System.out.println(album.printAlbum());
						}
					}
				}
				break;
			case "5":
				System.out.println("Enter playlist name: ");
				String name = scanner.nextLine().trim();

				if (name.equals("Top Rated")) {
					PlayList topRatedList = model.topRated();
					System.out.println("\nTop Rated Playlist:\n");

					ArrayList<Song> topRatedSongs = topRatedList.getPlaylist();
					if (topRatedSongs.size() == 0) {
						System.out.println(
								"No songs in the Top Rated playlist. Rate songs 4 or higher to add them here.\n");
					} else {
						java.util.Set<String> uniqueSongs = new java.util.HashSet<>();
						for (Song s : topRatedSongs) {
							String songIdentifier = s.getTitle() + "," + s.getArtist() + "," + s.getAlbum().getTitle();
							if (uniqueSongs.add(songIdentifier)) {
								System.out.println(s.printSong());
							}
						}

						System.out.println("\nWould you like to shuffle this playlist? (yes/no) \n");
						String response = scanner.nextLine().trim().toLowerCase();

						if (response.equals("yes")) {
							ArrayList<Song> shuffledSongs = model.shufflePlayLists("Top Rated");
							System.out.println("\nShuffled Top Rated Playlist:\n");

							uniqueSongs.clear();
							for (Song s : shuffledSongs) {
								String songIdentifier = s.getTitle() + "," + s.getArtist() + ","
										+ s.getAlbum().getTitle();
								if (uniqueSongs.add(songIdentifier)) {
									System.out.println(s.printSong());
								}
							}
						} else if (response.equals("no")) {
							System.out.println("Playlist not shuffled.\n");
						} else {
							System.out.println("Invalid response. Please answer 'yes' or 'no'.\n");
						}
					}
				} else {
					ArrayList<String> list = model.getPlaylists();
					boolean playlistFound = false;

					for (String namePlay : list) {
						if (namePlay.equals(name)) {
							playlistFound = true;

							System.out.println("\n" + name + "\n");

							PlayList playList = model.getPlayList(name);
							ArrayList<Song> listSongs = playList.getPlaylist();

							if (listSongs.size() == 0) {
								System.out.println("No songs in this playlist.\n");
							} else {
								java.util.Set<String> uniqueSongs = new java.util.HashSet<>();
								for (Song s : listSongs) {
									String songIdentifier = s.getTitle() + "," + s.getArtist() + ","
											+ s.getAlbum().getTitle();
									if (uniqueSongs.add(songIdentifier)) {
										System.out.println(s.printSong());
									}
								}

								System.out.println("\nWould you like to shuffle this playlist? (yes/no) \n");
								String response = scanner.nextLine().trim().toLowerCase();

								if (response.equals("yes")) {
									ArrayList<Song> shuffledSongs = model.shufflePlayLists(namePlay);
									System.out.println("\nShuffled playlist:\n");

									uniqueSongs.clear();
									for (Song s : shuffledSongs) {
										String songIdentifier = s.getTitle() + "," + s.getArtist() + ","
												+ s.getAlbum().getTitle();
										if (uniqueSongs.add(songIdentifier)) {
											System.out.println(s.printSong());
										}
									}
								} else if (response.equals("no")) {
									System.out.println("Playlist not shuffled.\n");
								} else {
									System.out.println("Invalid response. Please answer 'yes' or 'no'.\n");
								}
							}
							break;
						}
					}

					if (!playlistFound) {
						System.out.println("Invalid entry. Playlist not found in the library.\n");
					}
				}
				break;

			case "6":
				System.out.println("Enter song genre: ");
				String genre = scanner.nextLine().trim();
				System.out.println("\n" + model.searchSongByGenre(genre) + "\n");
				break;
			default:
				System.out.println("\nInvalid Entry\n");
		}
	}

	// searchMusicStore() - search for available songs and albums
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
				System.out.println("\nRESULTS:");
				System.out.println(store.searchSongsByTitle(title) + "\n");
				break;
			case "2":
				System.out.println("Enter song artist: ");
				String artist = scanner.nextLine().trim();
				System.out.println("\nRESULTS:");
				System.out.println(store.searchSongsByArtist(artist) + "\n");
				break;
			case "3":
				System.out.println("Enter album title: ");
				String albName = scanner.nextLine().trim();
				System.out.println("\nRESULTS:");
				System.out.println(store.searchAlbumByTitle(albName) + "\n");
				break;
			case "4":
				System.out.println("Enter album artist: ");
				String name = scanner.nextLine().trim();
				System.out.println("\nRESULTS:");
				System.out.println(store.searchAlbumByArtist(name) + "\n");
				break;
			default:
				System.out.println("\nInvalid Entry\n");
		}
	}

	// displayOptions() - show the client options
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
		System.out.println("9 - Play a song");
		System.out.println("10 - Remove from your library");
		System.out.println("11 - Logout");
	}

	// login() - allows user to login to music library can create users saving them
	// to database
	public void login() {
		boolean loginSuccessful = false;

		while (!loginSuccessful) {
			System.out.println("Please log in:");

			System.out.print("Username: ");
			String username = scanner.nextLine().trim();

			System.out.print("Password: ");
			String password = scanner.nextLine().trim();

			if (User.validateUser(username, password)) {
				loginSuccessful = true;
				currentUser = username;

				// Load the user's library
				UserLibrary.loadUserLibrary(username, model);

				System.out.println("\nWelcome to your Music Library!\n");
			} else {
				// Check if the user exists but password is wrong
				boolean userExists = false;

				// Try to create a new user with the credentials
				boolean newUserCreated = User.isUser(username, password);

				if (!newUserCreated) {
					System.out.println("\nPassword incorrect. Please try again.\n");
				} else {
					currentUser = username;
					loginSuccessful = true;
					System.out.println("\nWelcome to your Music Library!\n");
				}
			}
		}
	}

	// main(String[] args) - entry point of program
	public static void main(String[] args) {
		View view = new View();
		view.run();
	}
}