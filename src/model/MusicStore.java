package model;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;

/*
* File: MusicStore.java
* Author: Rees Hart
* Purpose: This class represents the music store  
*/
public class MusicStore {
	/* INSTANCE VARIABLES */
	private ArrayList<Album> albumList;
	
	/* CONSTRUCTOR*/
	public MusicStore(String filename) {
		this.albumList = new ArrayList<Album>();
		try {
			Scanner scanner = new Scanner(new File(filename));
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				createAlbum(line);
			}
		} catch (Exception e) {
			System.out.println("File error");
            System.exit(1);
		}
	}

	private Album createAlbum(String line) {
		return null;
		
		
	}
	
}
