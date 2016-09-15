package com.fuzzystream;

import java.util.Scanner;

import com.fuzzystream.fif_core.Attribute;
import com.fuzzystream.fif_core.DescriptionBasedFilter;
import com.fuzzystream.fif_core.Filter;
import com.fuzzystream.fif_core.FuzzySet;
import com.fuzzystream.fif_core.Metadata;
import com.fuzzystream.fif_core.PossibilisticInterpretation;
import com.fuzzystream.fif_core.SequenceFilter;

public class ProfileD extends AbstractProfile {
	
	private static final int YEAR_LENGTH = 4;
	
	static ProfileD profileD = null;
	
	public static ProfileD getInstanceProfileD() throws Exception{
		if(profileD == null){
			profileD = new ProfileD();
			profileSelected = profileD;
			return profileD;
		}
		else
			return profileD;
	}
	
	private ProfileD() throws Exception {
		
		id = 4;
		askForGenres();
		askForYears();
		
		while(profileGenreFs.getFuzzySet().isEmpty() && profileYearFs.getFuzzySet().isEmpty()){
			System.out.println("Insert values!!");
			askForGenres();
			askForYears();
		}
		
		if(profileGenreFs.getFuzzySet().isEmpty()){
			//costruzione metadato e filtro semplice
			Attribute year = new Attribute("Year");
			Metadata profileYearMd = new Metadata(year, profileYearFs, PossibilisticInterpretation.getinstance());
			DescriptionBasedFilter filterYear = new DescriptionBasedFilter(profileYearMd);
			generalFilter = filterYear;
		}
		
		else if(profileYearFs.getFuzzySet().isEmpty()){
			//costruzione metadato e filtro semplice
			Attribute genre = new Attribute("Genre");
			Metadata profileGenreMd = new Metadata(genre, profileGenreFs, PossibilisticInterpretation.getinstance());
			DescriptionBasedFilter filterGenre = new DescriptionBasedFilter(profileGenreMd);
			generalFilter = filterGenre;
		}
		
		else{
			//costruzione metadato e filtro semplice
			Attribute genre = new Attribute("Genre");
			Metadata profileGenreMd = new Metadata(genre, profileGenreFs, PossibilisticInterpretation.getinstance());
			DescriptionBasedFilter filterGenre = new DescriptionBasedFilter(profileGenreMd);
			
			//costruzione metadato e filtro semplice
			Attribute year = new Attribute("Year");
			Metadata profileYearMd = new Metadata(year, profileYearFs, PossibilisticInterpretation.getinstance());
			DescriptionBasedFilter filterYear = new DescriptionBasedFilter(profileYearMd);
			
			//costruzione filtro composto in sequenza
			Filter filter = new SequenceFilter(filterGenre, filterYear);
			generalFilter = filter;
		}
		
	}
	
	private void askForGenres() throws Exception{
		//richiesta informazioni riguardo genere
		System.out.println("For each of the following genres, set a value between 0 and 1. If you do not like a genre, insert 0. If it is your favourite genre insert 1.");
		Thread.sleep(1000);
		Scanner in = new Scanner(System.in);
		double value = 0.0;
		profileGenreFs = new FuzzySet();
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
				profileGenreFs.setValue(genre, value);
		}	
		
	}
	
	private void askForYears() throws Exception{
		//richeista infromazioni riguardo anno
		System.out.println("Please insert how many years you want to save: ");
		Scanner in2 = new Scanner(System.in);
		int numeroAnni = in2.nextInt();
		
		
		System.out.println("Please insert the years with whom you want to do filtering.");
		profileYearFs = new FuzzySet();
		String year = "2000";
		double value = 0.0;
		for(int i = 0; i < numeroAnni; i++){
			System.out.println("Insert year:");
			year = in2.nextLine();
			while(!isCorrectYear(year)){
				System.out.println("Please insert a valid year.");
				year = in2.nextLine();
			}	
			
			System.out.println("Insert value between 0 and 1: ");
			value = in2.nextDouble();
			while(value > 1 || value < 0){
				System.out.println("Please insert a correct value.");
				value = in2.nextDouble();
			}
				
			profileYearFs.setValue(year, value);
		}
		
		
	}
	
	private boolean isCorrectYear(String y){
		if(y.length() == YEAR_LENGTH){
			if((y.charAt(0) == '1')){
				if(y.charAt(1) == '9'){
					if(y.charAt(2) >= '0' && y.charAt(2) <= '9'){
						if(y.charAt(3) >= '0' && y.charAt(3) <= '9')
							return true;
					}
				}
			}
		}
		
		return false;
	}

	
}
