package com.fuzzystream;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;

import com.fuzzystream.fif_core.Filter;
import com.fuzzystream.fif_core.FuzzySet;

public abstract class AbstractProfile {
	
	protected static AbstractProfile profileSelected = null;
	
	
	protected static final double MIN_VALUE = 0;
	protected static final double MAX_VALUE = 1;
	
	protected static int id;
	//private static int idFactory = 4;
	
	protected static FuzzySet profileGenreFs; 
	protected static FuzzySet profileYearFs; 
	protected LinkedList<Movie> interestingMovies = new LinkedList<Movie>();
	protected Filter generalFilter;
	
	public static AbstractProfile getInstance(){
		return profileSelected;
	}
	
	public Filter getFilter(){
		return this.generalFilter;
	}
	
	public FuzzySet getGenrePreferences() {
		return profileGenreFs;
	}
	
	public FuzzySet getYearPreferences() {
		return this.profileYearFs;
	}
	
	public LinkedList<Movie> getInterestingMovies(){
		return this.interestingMovies;
	}
	
	public void addInterestingMovie(Movie movie){
		this.interestingMovies.add(movie);
	}
	
	public void showInterestingMovies(){
		
		if(interestingMovies.isEmpty()){
			System.out.println("NON STA UN CAZZO DI NIENTE");
			return;
		}
		//Collections.sort(this.interestingMovies);
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
	
	
	
	


}
