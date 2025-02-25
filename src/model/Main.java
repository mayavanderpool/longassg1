package model;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class Main {
	public static void main(String[] args) {
		MusicStore store = new MusicStore();
		
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
					store.addAlbum(album);
					
					songFile(album);
					
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
	
	private static void songFile(Album album) {
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
						Song song = new Song(songLine, album.getArtist());
						album.addSong(song);
					}
				}
				
				scanner.close();
			}
		} catch (FileNotFoundException exception) {
			System.out.println("File does not exist");
			exception.printStackTrace();
		}
	}
}
