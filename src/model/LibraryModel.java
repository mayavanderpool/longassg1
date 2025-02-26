package model;

import java.util.ArrayList;

/*
 * File: LibraryModel.java
 * Author: Maya Vanderpool
 * Purpose: This is a test class for Song.java.
 */

public class LibraryModel {

    /* INSTANCE VARIABLES */
    private ArrayList<Song> songs;
    private ArrayList<PlayList> playLists;
    private ArrayList<Album> albums;

    public LibraryModel(){
        this.songs = new ArrayList<Song>();
        this.playLists = new ArrayList<PlayList>();
        this.albums = new ArrayList<Album>();
    }

    public void addSong(Song song){
        songs.add(new Song(song));
    }

    public void addPlaylist(PlayList playlist){
        playLists.add(playlist);
    }

    public void addAlbum(Album album){
        albums.add(album);
    }

    public String searchSongsByTitle (String title) {
		for(Song s: songs) {
			if (s.getTitle() == title) {
				return s.printSong();
			}
            System.out.println("This song does not exist in the library.");	
		}
	}

    public String searchSongsByArtist (String artist) {
		for(Song s: songs) {
			if (s.getArtist() == artist) {
				return s.printSong();
			}
			System.out.println("This song does not exist in the library.");	
		}
	}

    public String searchAlbumByTitle(String title) {
		for (Album a: albums) {
			if(a.getTitle() == title) {
				return a.printAlbum();
			}
            System.out.println("This album does not exist in the library.");	
        }
	}

    public String searchAlbumByArtist(String artist) {
		for (Album a: albums) {
			if(a.getArtist() == artist) {
				return a.printAlbum();
			}
		}
		System.out.println("This album does not exist in the library.");	
	}

    public void searchPlayList (String name) {
		for(PlayList playList: playLists) {
			if (playList.getName() == name) {
				for(Song s: playList.getPlaylist()){
                    s.printSong();
                }
			}
		}
        System.out.println("This playlist does not exist in the library.");	
	}


    
}
