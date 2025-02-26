package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/*
* File: MusicStore.java
* Author: Rees Hart
* Purpose: This class represents the music store  
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
	
	public void loadStore() {
		String file = "albums.txt";
		
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

	private void songFile(Album album) {
		String file = album.getTitle() + "_" + album.getArtist() +".txt";
		
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
		
		
	public void addAlbum(Album album) {
		albumList.add(album);
	}
	
	public ArrayList<Album> getAlbumList(){
		ArrayList<Album> albums = new ArrayList<Album>();
		for (Album a : albumList) {
			albums.add(new Album(a.getTitle(), a.getArtist()));
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
	
	public void addSongs(Album album) {
		ArrayList<Song> albumsSongs = album.getSongList();
		for (Song s: albumsSongs) {
			songList.add(s);
		}
	}
	
	public void searchSongsByTitle (String title) {
		boolean found = false;
		for(Song s: songList) {
			if (s.getTitle() == title) {
				found = true;
				s.printSong();
			}
			
		}
		if (found == false) {
			System.out.println("This song does not exist in the music store.");
		}
		
	}
	
	
	public void searchSongsByArtist (String artist) {
		boolean found = false;
		for(Song s: songList) {
			if (s.getArtist() == artist) {
				found = true;
				s.printSong();
			}
			
		}
		if (found == false) {
			System.out.println("This song does not exist in the music store.");
		}
	}
	
	public void searchAlbumByTitle(String title) {
		boolean found = false;
		for (Album a: albumList) {
			if(a.getTitle() == title) {
				a.printAlbum();
				found = true;
			}
		}
		if (found == false) {
			System.out.println("This album does not exist in the music store.");
		}
	}
	
	public void searchAlbumByArtist(String artist) {
		boolean found = false;
		for (Album a: albumList) {
			if(a.getArtist() == artist) {
				a.printAlbum();
				found = true;
			}
		}
		if (found == false) {
			System.out.println("This album does not exist in the music store.");
		}
	}
	
}