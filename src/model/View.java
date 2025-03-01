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
	private Scanner scanner;
	private LibraryModel model;
	private MusicStore store; 

	public View() {
		this.model = new LibraryModel();
		this.store = new MusicStore();
		this.scanner = new Scanner(System.in);
	}

	//run() - begins program
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
					System.out.println("/nInvalid Entry/n");
			}

		}
	}

	//exit() - exits program
	private void exit() {
		System.out.println("Exiting Music Library!");
		scanner.close();
		System.exit(0);

	}

	//rateSong() - allows client to rate song
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
				if(num >= 1 && num <=5) {
					s.setRating(num);
					System.out.println("\n" + songName + " rated: " + num + "\n");
				} else {
					System.out.println("\nInvalid input! \n");
				}
			}
		}

	}

	//markFav() - allows client to mark a song as favorite
	private void markFav() {
		System.out.println("Enter song title: ");
		String songName = scanner.nextLine().trim();
		ArrayList<Song> songs = model.getSong(songName);
		if (songs.size() == 0) {
			System.out.println("This song does not exist in the library.");
		} else {
			for (Song s : songs) {
				s.setFavorite();
				System.out.println("\n" + songName + " has been marked favorite \n");
			}
		}

	}

	//addOrRemovePlaylist() - add or remove songs from the playlist in library
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
					System.out.println("/nThis song does not exist in the library. /n");
				} else {
					for (Song s : songs) {
						list.addSong(s);
						System.out.println("\nSong added \n");
					}
					System.out.println(list.getPlaylist().size());
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

	//createPlaylist() - create a new playlist
	private void createPlaylist() {
		System.out.println("Enter playlist name: ");
		String name = scanner.nextLine().trim();
		PlayList newPlay = new PlayList(name);
		model.addPlaylist(newPlay);
		System.out.println("\nNew Playlist: '" + name + "' created \n");

	}

	//seeLibrary() - view the library
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
					System.out.println(al.printAlbum()+ "/n");
				}
				break;
			case "4":
				ArrayList<String> lists = model.getPlaylists();
				if(lists.size() != 0) {
					System.out.println("\nPlaylists: \n");
				} else {
					System.out.println("\nNo playlists in library \n");
				}
				
				for (String li : lists) {
					System.out.println(li+ "\n");
				}
				break;

			case "5":
				ArrayList<String> favs = model.getFavorites();
				if (favs.size() != 0) {
					System.out.println("\nFavorites: \n");
				} else {
					System.out.println("\nNo favorites in library \n");
				}
				for (String fav : favs) {
					System.out.println(fav + "\n");
				}
				break;
			default:
				System.out.println("\nInvalid Entry\n");

		}

	}

	//addtoLibrary() - add songs or albums from the music store to library
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
				if(found2 == true) {
					System.out.println("\nAlbum added to library \n");
				} else {
					System.out.println("\nUnable to add album \n");
				}
				break;
			default:
				System.out.println("\nInvalid Entry\n");
		}
	}

	//searchLibrary() - search for specific songs, albums, and playlists in library
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
					System.out.println("\nThis song does not exist in the library.\n");
				} else {
					for (Song s : songs) {
						System.out.println("\n" + s.printSong() + "\n");
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
				System.out.println("\n" + model.searchAlbumByTitle(albTitle) + "\n");
				break;
			case "4":
				System.out.println("Enter album artist: ");
				String albArtist = scanner.nextLine().trim();
				System.out.println( "\n" + model.searchAlbumByArtist(albArtist) + "\n" );
				break;
			case "5":
				System.out.println("Enter playlist name: ");
				String name = scanner.nextLine().trim();
				
				ArrayList<String> list = model.getPlaylists();
		
				for (String namePlay : list) {
					if(namePlay.equals(name)) {
						System.out.println("\n" + name + "\n");
						
						PlayList playList = model.getPlayList(name);
						
						ArrayList<Song> listSongs = playList.getPlaylist();
						for(Song s : listSongs) {
							System.out.println(s.printSong());
						}
					}
				}

				break;
			default:
				System.out.println("\nInvalid Entry\n");
		}

	}

	//searchMusicStore() - search for available songs and albums
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

	//displayOptions() - show the client options
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

	public static void main(String[] args) {
		View view = new View();
		view.run();
	}
	

}
