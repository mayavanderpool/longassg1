package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/*
* File: MusicStore.java
* Author: Rees Hart and Maya Vanderpool
* Purpose: This class represents the music store. It loads the store using a 
* text file with all the albums and various text files that representing the 
* album contents.   
*/
public class MusicStore {
	/* INSTANCE VARIABLES */
	private ArrayList<Album> albumList;
	private ArrayList<Song> songList;

	
	/* CONSTRUCTOR*/
	public MusicStore() {
		this.albumList = new ArrayList<Album>();
		this.songList = new ArrayList<Song>();
		loadStore();
	}  
	
	// loadStore() -- reads from albums.txt and creates album objects for each line
	public void loadStore() {
		String file = "albums/albums.txt";
		
		try {
			Scanner scanner = new Scanner(new File(file));
			
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				String[] split = line.split(",");
				
				if (split.length == 2) {
					String title = split[0].trim();
					String artist = split[1].trim();
					 
					Album album = new Album(title, artist);
					addAlbum(album);
					
					songFile(album);
					
					addSongs(album);
					
				} else {
					System.out.println("Invalid line");
				}
			}
			
			scanner.close();
		} catch (FileNotFoundException exception) {
			System.out.println("File does not exist");
			exception.printStackTrace();
		}
		
		
		
	}
	
	// songFile(Album album) -- adds the songs to the music store from each album
	private void songFile(Album album) {
		String file = "albums/" + album.getTitle() + "_" + album.getArtist() +".txt";
		
		try {
			Scanner scanner = new Scanner(new File(file));
			
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				String[] split = line.split(",", 4);
				
				if(split.length == 4) {
					album.setGenre(split[2].trim());
					album.setYear(Integer.parseInt(split[3].trim()));
				}
				
				while (scanner.hasNextLine()) {
					String songLine = scanner.nextLine().trim();
					if(!songLine.isEmpty()) {
						Song song = new Song(songLine, album.getArtist(), album);
						album.addSong(song);
					}
				}
				
				
			}
			scanner.close();
		} catch (FileNotFoundException exception) {
			System.out.println("File does not exist");
			exception.printStackTrace();
		}
	}
		
	// addAlbum(Album album) -- adds an album to the music stores album list
	public void addAlbum(Album album) {
		albumList.add(album);
	}
	
	/* GETTERS */
	public ArrayList<Album> getAlbumList(){
		ArrayList<Album> albums = new ArrayList<Album>();
		for (Album a : albumList) {
			albums.add(a.deepCopy());
		}
		return albums;
	}
	

	public ArrayList<Song> getSongList(){
		ArrayList<Song> songs = new ArrayList<Song>();
		for (Song s : songList) {
			songs.add(new Song(s));
		}
		return songs;
	}
	
	// addSongs(Album album) -- adds the songs from album to the music store song list
	public void addSongs(Album album) {
		ArrayList<Song> albumsSongs = album.getSongList();
		for (Song s: albumsSongs) {
			songList.add(s);
		}
	}
	
	// searchSongsByTitle (String title) -- searches for a song by title and returns the song info if found
	public String searchSongsByTitle (String title) {
		boolean found = false;
		String out = "";
		for(Song s: songList) {
			if (s.getTitle().equals(title)) {
				found = true;
				out += s.printSong();
			}
		}
		if (found == false) {
			out += "This song does not exist in the music store.";
		}
		return out;
	}
	
	//searchSongsByArtist (String artist) -- returns songs by artist if there are any 
	public String searchSongsByArtist (String artist) {
		boolean found = false;
		String out = "";
		for(Song s: songList) {
			if (s.getArtist().equals(artist)) {
				found = true;
				out += s.printSong() + "\n";
			}
			
		}
		if (found == false) {
			out += "This artist does not exist in the music store.";
		}
		return out;
	}
	
	// String searchAlbumByTitle(String title) -- return albums that have the title 
	public String searchAlbumByTitle(String title) {
		boolean found = false;
		String out = "";
		for (Album a: albumList) {
			if(a.getTitle().equals(title)) {
				out+= a.printAlbum() + "\n";
				found = true;
			}
		}
		if (found == false) {
			out += "This album does not exist in the music store.";
		}
		return out;
	}
	
	// searchAlbumByArtist(String artist) -- returns albums by artist
	public String searchAlbumByArtist(String artist) {
		boolean found = false;
		String out = "";
		for (Album a: albumList) {
			if(a.getArtist().equals(artist)) {
				out += a.printAlbum() + "\n";
				found = true;
			}
		}
		if (found == false) {
			out += "This artist does not exist in the music store.";
		}
		return out;
	}
	
}