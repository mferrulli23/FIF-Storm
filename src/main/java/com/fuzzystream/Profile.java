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
	
	private static int id;
	private FuzzySet genrePreferences;
	private LinkedList<Movie> interestingMovies;
	
	private FuzzySet yearPreferences;
	
	
	private Profile(int i) throws Exception {
		switch (i){
		case 1: id = 1;
				genrePreferences = new FuzzySet();
				genrePreferences.setValue("Action", 0.8);
				genrePreferences.setValue("Thriller", 0.7);
				genrePreferences.setValue("Mystery", 0.5);
				
				
				interestingMovies = new LinkedList<Movie>();
				
				yearPreferences = new FuzzySet();
				yearPreferences.setValue("1986", 0.4);
				yearPreferences.setValue("1997", 0.8);
				
				break;
		
		case 2: id = 2;
				genrePreferences = new FuzzySet();
				genrePreferences.setValue("Comedy", 0.9);
				genrePreferences.setValue("Children", 0.8);
				genrePreferences.setValue("Adventure", 0.6);
				interestingMovies = new LinkedList<Movie>();
				break;
		
		case 3: id = 3;
				genrePreferences = new FuzzySet();
				genrePreferences.setValue("Crime", 1);
				genrePreferences.setValue("War", 0.7);
				genrePreferences.setValue("Western", 0.3);
				interestingMovies = new LinkedList<Movie>();
				break;
		}
		
	}
	
	public static Profile getInstance() throws Exception{
		if (profileSelected == null)
			Profile.profileSelected = new Profile(id);
		return profileSelected;
	}

	public int getId() {
		return id;
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
		
	}
	
	public static void profileSelection() throws Exception{
    	System.out.println("**Please select an option:**");
    	System.out.println("1) Load a existing demo user profile");
    	System.out.println("2) Create a new user profile");
    	System.out.println("Enter 1 or 2...");
    	Scanner in = new Scanner(System.in);
    	int option = in.nextInt();
    	if(option == 2)
    		createNewProfile();
    	else
    		loadExistingProfile();
    	
    	//da rendere piu robusto
    	
    }
	
	private static void loadExistingProfile() throws Exception {
		System.out.println("Choose an existing profile:");
		System.out.println("1: Action = 0.8, Thriller = 0.7, Mystery = 0.5");
		System.out.println("2: Comedy = 0.9, Children = 0.8, Adventure = 0.6");
		System.out.println("3: Crime = 1, War = 0.7, Western = 0.3");
		System.out.println("Insert profile number: ");
		Scanner in = new Scanner(System.in);
		int scelta = in.nextInt();
		if(scelta == 1)
			profileSelected = new Profile(1);
		else if(scelta == 2)
			profileSelected = new Profile(2);
		else if(scelta == 3)
			profileSelected = new Profile(3);	
		
		//da rendere più robusto
	}

	private static void createNewProfile(){
		//da implementare
	}

	
	

}
