package com.fuzzystream.fif_storm;

import java.util.HashMap;

public class Utility {
	
	public static HashMap<Integer, String> genresIndexes = new HashMap<Integer, String>();
	
	public static void setGenres(){
		genresIndexes.put(4, "unknown");
		genresIndexes.put(5, "Action");
		genresIndexes.put(6, "Adventure");
		genresIndexes.put(7, "Animation");
		genresIndexes.put(8, "Children");
		genresIndexes.put(9, "Comedy");
		genresIndexes.put(10, "Crime");
		genresIndexes.put(11, "Documentary");
		genresIndexes.put(12, "Drama");
		genresIndexes.put(13, "Fantasy");
		genresIndexes.put(14, "FilmNoir");
		genresIndexes.put(15, "Horror");
		genresIndexes.put(16, "Musical");
		genresIndexes.put(17, "Mystery");
		genresIndexes.put(18, "Romance");
		genresIndexes.put(19, "SciFi");
		genresIndexes.put(20, "Thriller");
		genresIndexes.put(21, "War");
		genresIndexes.put(22, "Western");
	}

}
