package com.fuzzystream;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;

import com.fuzzystream.fif_core.FuzzySet;

public class Profile {
	
	private static Profile profileSelected = null;
	
	
	private static final double MIN_VALUE = 0;
	private static final double MAX_VALUE = 1;

	private static int id;
	private static int idFactory = 4;
	private static FuzzySet genrePreferences;
	private LinkedList<Movie> interestingMovies = new LinkedList<Movie>();
	//private FuzzySet yearPreferences;
	
	private Profile(int i) throws Exception {
		switch (i){
		case 1: id = 1;
				genrePreferences = new FuzzySet();
				genrePreferences.setValue("Action", 0.8);
				genrePreferences.setValue("Thriller", 0.7);
				genrePreferences.setValue("Mystery", 0.5);
				//interestingMovies = new LinkedList<Movie>();
				//yearPreferences = new FuzzySet();
				//yearPreferences.setValue("1986", 0.4);
				//yearPreferences.setValue("1997", 0.8);
				break;
		
		case 2: id = 2;
				genrePreferences = new FuzzySet();
				genrePreferences.setValue("Comedy", 0.9);
				genrePreferences.setValue("Children", 0.8);
				genrePreferences.setValue("Adventure", 0.6);
				break;
		
		case 3: id = 3;
				genrePreferences = new FuzzySet();
				genrePreferences.setValue("Crime", 1);
				genrePreferences.setValue("War", 0.7);
				genrePreferences.setValue("Western", 0.3);
				break;
		}
		
	}
	
	private Profile(){
		
	}
	
	
	public static Profile getInstance() throws Exception{
			return profileSelected;
	}

	public FuzzySet getGenrePreferences() {
		return genrePreferences;
	}
	
	public LinkedList<Movie> getInterestingMovies(){
		return this.interestingMovies;
	}
	
	public void addInterestingMovie(Movie movie){
		this.interestingMovies.add(movie);
	}
	
	public void showInterestingMovies(){
		Collections.sort(this.interestingMovies);
		for(Movie m : interestingMovies){
			System.out.println("Titolo: " + m.getTitle() + "  MatchingDegree: " + m.getGrade());
		}
	}
	
	public void printTxt() throws IOException{
		FileWriter w = new FileWriter("output.txt");
		BufferedWriter bw = new BufferedWriter(w);
		String movie = "";
		int movieNum = 0;
		for(Movie m : interestingMovies){
			movie = movieNum + ". " + "Titolo: " + m.getTitle() + "  MatchingDegree: " + m.getGrade() + "\n";
			bw.write(movie);
			movieNum++;
		}
		bw.close();
		
	}
	
	public static void profileSelection() throws Exception{
    	System.out.println("**Please select an option:**");
    	System.out.println("1) Load a existing demo user profile");
    	System.out.println("2) Create a new user profile");
    	System.out.println("Enter 1 or 2...");
    	Scanner in = new Scanner(System.in);
    	int option = in.nextInt();
    	while (option != 1 && option != 0){
    		System.out.println("Please insert a correct choice (1 or 2): ");
    		option = in.nextInt();
    	}
    	
    	if(option == 2)
    		createNewProfile();
    	else
    		loadExistingProfile();
 
    	
    }
	
	private static void loadExistingProfile() throws Exception {
		System.out.println("Choose an existing profile:");
		System.out.println("1: Action = 0.8, Thriller = 0.7, Mystery = 0.5");
		System.out.println("2: Comedy = 0.9, Children = 0.8, Adventure = 0.6");
		System.out.println("3: Crime = 1, War = 0.7, Western = 0.3");
		System.out.println("Insert profile number: ");
		Scanner in = new Scanner(System.in);
		int scelta = in.nextInt();
		while (scelta != 1 && scelta != 2 && scelta != 3){
    		System.out.println("Please insert a correct choice (1 or 2 or 3): ");
    		scelta = in.nextInt();
    	}
		if(scelta == 1)
			profileSelected = new Profile(1);
		else if(scelta == 2)
			profileSelected = new Profile(2);
		else if(scelta == 3)
			profileSelected = new Profile(3);	
	}

	private static void createNewProfile() throws Exception{
		id = ++idFactory;
		System.out.println("For each of the following genres, set a value between 0 and 1. If you do not like a genre, insert 0. If it is your favourite genre insert 1.");
		Thread.sleep(1000);
		Scanner in = new Scanner(System.in);
		double value = 0.0;
		genrePreferences = new FuzzySet();
		String[] genres = {"Action", "Adventure", "Animation", "Children", "Comedy", "Crime", "Documentary", "Drama", "Fantasy", "FilmNoir", "Horror", "Musical", "Mystery", 
				"Romance", "SciFi", "Thriller", "War", "Western"};
		for(String genre : genres){
			System.out.println(genre + ": ");
			value = in.nextDouble();
			while (value > MAX_VALUE || value < MIN_VALUE){
				System.out.println("Please insert a correct value. It must be between 0 and 1:");
				value = in.nextDouble();
			}
			if(value != 0)
				genrePreferences.setValue(genre, value);
		}
		
		profileSelected = new Profile();
		in.close();
	}
	
	/*public int getId() {
		return id;
	}
	
	public void setId(int i){
		this.id = i;
	}
*/
	
	

}
